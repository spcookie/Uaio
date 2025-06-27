package io.net.components.dto

import io.micronaut.serde.annotation.Serdeable
import kotlin.properties.Delegates

@Serdeable
class Result<T> {

    var code by Delegates.notNull<Int>()

    var message: String? = null

    var data: T? = null

    companion object {

        fun success(): Result<Unit> {
            val result = Result<Unit>()
            result.code = ResultCode.Success.code
            return result
        }

        fun <T> success(data: T?): Result<T> {
            val result = Result<T>()
            result.code = ResultCode.Success.code
            result.data = data
            return result
        }

        fun <T> fail(code: Int, message: String?): Result<T> {
            val result = Result<T>()
            result.code = code
            result.message = message
            return result
        }

        fun <T> fail(message: String?): Result<T> {
            val result = Result<T>()
            result.code = ResultCode.Fail.code
            result.message = message
            return result
        }

        fun <T> fail(): Result<T> {
            val result = Result<T>()
            result.code = ResultCode.Fail.code
            return result
        }

    }


}