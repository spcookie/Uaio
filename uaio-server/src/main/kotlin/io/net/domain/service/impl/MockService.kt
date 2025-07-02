package io.net.domain.service.impl

import io.micronaut.transaction.annotation.Transactional
import io.net.components.domain.ID
import io.net.components.domain.scalar
import io.net.components.excption.BusinessException
import io.net.domain.model.entity.Mock
import io.net.domain.model.entity.MockEngine
import io.net.domain.model.entity.MockServer
import io.net.domain.model.valueobject.MockServerConfig
import io.net.domain.model.valueobject.MockTreeConfig
import io.net.domain.repository.MockRepository
import io.net.domain.service.IMockService
import jakarta.annotation.PreDestroy
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class MockService(
    val mockRepository: MockRepository
) : IMockService {

    private val mockEngine by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        MockEngine().apply {
            start()
            mockEngineStarted = true
        }
    }

    private var mockEngineStarted = false

    private val mutex = Mutex()

    private lateinit var mockServer: MockServer

    @PreDestroy
    fun destroy() {
        if (mockEngineStarted) {
            mockEngine.stop()
        }
    }

    override suspend fun generate(template: String): String {
        return mockEngine.mock(template)
    }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun save(mock: Mock) {
        val mocks = mockRepository.findByMethodAndPath(mock.config.method, mock.config.path)
        if (mocks.singleOrNull() != null) {
            throw BusinessException(message = "已存在相同方法、路径的Mock")
        }
        mockRepository.save(mock)
        if (::mockServer.isInitialized) {
            mockServer.addMock(mock)
        }
    }

    override suspend fun list(): Flow<Mock> {
        return mockRepository.list()
    }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun removeById(id: ID) {
        mockRepository.removeById(id)
        if (::mockServer.isInitialized) {
            mockServer.removeMock(id)
        }
    }

    override suspend fun updateById(mock: Mock) {
        val mocks =
            mockRepository.findAllByMethodAndPathAndIdNotEqual(mock.config.method, mock.config.path, mock.id)
        if (mocks.singleOrNull() != null) {
            throw BusinessException(message = "已存在相同方法、路径的Mock")
        }
        mockRepository.updateById(mock)
    }

    override suspend fun refreshServerConfig(config: MockServerConfig) {
        mutex.withLock {
            if (::mockServer.isInitialized) {
                if (mockServer.config.port != config.port) {
                    mockServer.stop()
                    mockServer = MockServer(config, mockEngine, list().toList())
                    mockServer.start()
                }
            }
        }
    }

    override suspend fun startServer(config: MockServerConfig): Boolean {
        return mutex.withLock {
            if (!::mockServer.isInitialized) {
                mockServer = MockServer(config, mockEngine, list().toList())
            }
            mockServer.start()
        }
    }

    override suspend fun stopServer(config: MockServerConfig) {
        mutex.withLock {
            if (::mockServer.isInitialized) {
                mockServer.stop()
            }
        }
    }

    override suspend fun getServerStats(): MockServer.Statue? {
        return if (::mockServer.isInitialized) {
            mockServer.statue
        } else {
            null
        }
    }

    override suspend fun listTree(): MockTreeConfig {
        val mocks = mockRepository.list()
            .map { mock ->
                MockTreeConfig(
                    id = mock.id.scalar,
                    method = mock.config.method,
                    path = mock.config.path,
                    headers = mock.config.headers,
                    args = mock.config.args,
                    template = mock.config.template,
                    children = mutableListOf()
                )
            }
            .toList()
            .toMutableList()
        val root = MockTreeConfig(
            path = "/",
            children = mutableListOf()
        )
        dfs(root, mocks)
        return root
    }

    private fun dfs(parent: MockTreeConfig, mocks: List<MockTreeConfig>, deep: Int = 1) {
        mocks.groupBy { mock ->
            val index = mock.path.indexOf('/', deep)
            if (index == -1) {
                mock.path
            } else {
                mock.path.substring(0, (index + 1).coerceAtMost(mock.path.length))
            }
        }.map { (k, v) ->
            MockTreeConfig(path = k.substringAfter('/'), children = mutableListOf()) to v
        }.forEach { (k, v) ->
            if (!k.path.endsWith("/")) {
                parent.children.addAll(v)
            } else {
                parent.children.add(k)
                dfs(k, v, k.path.length + 1)
            }
        }
    }

}