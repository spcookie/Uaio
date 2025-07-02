package io.net.interfaces.rest

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.net.components.dto.Result
import io.net.components.excption.BusinessException
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Controller
class GlobalExceptionProcessor {

    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionProcessor::class.java)
    }

    @Error(value = BusinessException::class, global = true)
    fun process(e: BusinessException): Mono<Result<String>> {
        log.error("global business exception: code=${e.code}, message=${e.message}", e)
        return Mono.just(Result.fail(e.code, e.message))
    }

    @Error(value = Exception::class, global = true)
    fun process(e: Exception): Mono<Result<String>> {
        log.error("global exception: message=${e.message}", e)
        return Mono.just(Result.fail(e.message))
    }

}