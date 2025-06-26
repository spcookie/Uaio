package io.net.domain.model.entity

import io.micronaut.core.io.ResourceLoader
import io.net.components.domain.Entity
import io.net.components.domain.GlobalUniqueID
import io.net.toolkit.BeanUtils
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.future.asCompletableFuture
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source

class MockEngine : Entity(GlobalUniqueID()) {

    private val scope = CoroutineScope(SupervisorJob() + CoroutineName("mock-engine-coroutine"))

    private val sendChannel = Channel<String>(10)

    private val receiveChannel = Channel<String>(10)

    private val _statue = atomic(Statue.INITIALIZATION)

    val statue: Statue
        get() = _statue.value

    enum class Statue {
        INITIALIZATION, RUNNING, STOPPED
    }

    fun start() {
        if (_statue.compareAndSet(Statue.INITIALIZATION, Statue.RUNNING)) {
            scope.launch {
                val loader = BeanUtils.getBean<ResourceLoader>()
                val mockJs = loader.getResourceAsStream("classpath:js/mock.js")
                    .orElseThrow()
                    .readAllBytes()
                    .toString(Charsets.UTF_8)
                Context.newBuilder("js")
                    .build()
                    .use {
                        it.eval(Source.newBuilder("js", mockJs, "mock.js").build())
                        sendChannel.receiveAsFlow()
                            .onEach { template ->
                                val result = it.eval(Source.newBuilder("js", template, "mock.js").build()).asString()
                                receiveChannel.send(result)
                            }
                            .collect()
                    }
            }
        }
    }

    fun stop() {
        if (_statue.compareAndSet(Statue.RUNNING, Statue.STOPPED)) {
            scope.cancel()
        }
    }

    fun mock(template: String): String {
        if (_statue.value != Statue.RUNNING) {
            throw IllegalStateException("mock engine is not running")
        }
        return scope.async {
            sendChannel.send(template)
            receiveChannel.receive()
        }.asCompletableFuture()
            .get()
    }

}