package io.net.infrastructure.config

import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.micronaut.scheduling.annotation.Async
import jakarta.annotation.PreDestroy
import jakarta.inject.Singleton
import org.h2.tools.Server
import org.slf4j.LoggerFactory

@Singleton
class H2ConsoleStarter {

    companion object {
        private val log = LoggerFactory.getLogger(H2ConsoleStarter::class.java)
    }

    private var webServer: Server? = null

    @Async
    @EventListener
    fun onStartup(event: ServerStartupEvent) {
        webServer = Server.createWebServer(
            "-web", "-webAllowOthers", "-webPort", "8082"
        ).start()
        log.info("H2 console started at: http://localhost:8082")
    }

    @PreDestroy
    fun onShutdown() {
        webServer?.stop()
    }

}