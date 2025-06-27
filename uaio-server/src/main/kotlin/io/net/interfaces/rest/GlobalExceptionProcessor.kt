package io.net.interfaces.rest

import io.micronaut.context.annotation.Context
import io.micronaut.http.annotation.Error
import io.net.components.dto.Result
import io.net.components.excption.BusinessException
import reactor.core.publisher.Mono

@Context
class GlobalExceptionProcessor {

    @Error(value = BusinessException::class, global = true)
    fun process(e: BusinessException): Mono<Result<String>> {
        return Mono.just(Result.fail(e.code, e.message))
    }

    @Error(value = Exception::class, global = true)
    fun process(e: Exception): Mono<Result<String>> {
        return Mono.just(Result.fail(e.message))
    }

}