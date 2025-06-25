package io.net.domain.model.entity

import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.net.component.domain.Entity
import io.net.component.domain.GlobalUniqueID
import io.net.component.domain.ID
import io.net.domain.model.valueobject.MockServerConfig
import java.util.concurrent.CopyOnWriteArrayList

class MockServer(
    val config: MockServerConfig,
    val mockEngine: MockEngine,
    initMocks: MutableList<Mock> = mutableListOf<Mock>()
) : Entity(GlobalUniqueID()) {

    lateinit var server: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>

    val mocks: MutableList<Mock> = CopyOnWriteArrayList()

    init {
        mocks.addAll(initMocks)
    }

    fun start() {
        server = embeddedServer(Netty, config.port) {
            routing {
                route("{...}") {
                    handle {
                        dispatch()
                    }
                }
            }
        }.start()
    }

    fun stop() {
        if (::server.isInitialized) {
            server.stop()
        }
    }

    suspend fun RoutingContext.dispatch() {
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

    private fun RoutingContext.isMatch(mock: Mock) =
        HttpMethod.parse(mock.config.method.name) == call.request.httpMethod && mock.config.path == call.request.path()

}