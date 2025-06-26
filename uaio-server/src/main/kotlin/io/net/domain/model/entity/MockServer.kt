package io.net.domain.model.entity

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import io.net.components.domain.Entity
import io.net.components.domain.GlobalUniqueID
import io.net.components.domain.ID
import io.net.domain.model.valueobject.MockServerConfig
import kotlinx.atomicfu.atomic
import java.util.concurrent.CopyOnWriteArrayList

class MockServer(
    val config: MockServerConfig,
    val mockEngine: MockEngine,
    initMocks: List<Mock> = listOf<Mock>()
) : Entity(GlobalUniqueID()) {

    lateinit var server: NettyApplicationEngine

    val mocks: MutableList<Mock> = CopyOnWriteArrayList()

    private val _statue = atomic(Statue.INITIALIZATION)

    val statue: Statue
        get() = _statue.value

    enum class Statue {
        INITIALIZATION, RUNNING, STOPPED
    }

    init {
        mocks.addAll(initMocks)
    }

    fun start(): Boolean {
        if (!::server.isInitialized
            || _statue.compareAndSet(Statue.INITIALIZATION, Statue.RUNNING)
            || _statue.compareAndSet(Statue.STOPPED, Statue.RUNNING)
        ) {
            server = embeddedServer(Netty, config.port) {
                routing {
                    route("{...}") {
                        handle {
                            dispatch()
                        }
                    }
                }
            }.start()
            return true
        }
        return false
    }

    fun stop() {
        if (::server.isInitialized) {
            server.stop()
            _statue.value = Statue.STOPPED
        }
    }

    suspend fun PipelineContext<Unit, ApplicationCall>.dispatch() {
        var handled = false
        for (mock in mocks) {
            if (isMatch(mock)) {
                val result = mockEngine.mock(mock.config.template)
                call.respondText(result)
                handled = true
            }
        }
        if (!handled) {
            call.respondText("Not Mock Find", status = HttpStatusCode.NotFound)
        }
    }

    fun addMock(vararg mocks: Mock) {
        this.mocks.addAll(mocks.toList())
    }

    fun removeMock(vararg ids: ID) {
        ids.map { id ->
            mocks.removeIf { it.id == id }
        }
    }

    private fun PipelineContext<Unit, ApplicationCall>.isMatch(mock: Mock) =
        HttpMethod.parse(mock.config.method.name) == call.request.httpMethod && mock.config.path == call.request.path()

}