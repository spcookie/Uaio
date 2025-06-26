package io.net.domain.service.impl

import io.micronaut.transaction.annotation.Transactional
import io.net.components.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.model.entity.MockEngine
import io.net.domain.model.entity.MockServer
import io.net.domain.model.valueobject.MockServerConfig
import io.net.domain.repository.MockRepository
import io.net.domain.service.IMockService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class MockService(
    val mockRepository: MockRepository
) : IMockService {

    private val mockEngine = MockEngine()

    private val mutex = Mutex()

    private lateinit var mockServer: MockServer

    @PostConstruct
    fun init() {
        mockEngine.start()
    }

    @PreDestroy
    fun destroy() {
        mockEngine.stop()
    }

    override suspend fun generate(template: String): String {
        return mockEngine.mock(template)
    }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun save(mock: Mock) {
        mockRepository.save(mock)
        mockServer.addMock(mock)
    }

    override suspend fun list(): Flow<Mock> {
        return mockRepository.list()
    }

    override suspend fun removeById(id: ID) {
        mockRepository.removeById(id)
    }

    override suspend fun updateById(mock: Mock) {
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
            if (::mockServer.isInitialized) {
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

}