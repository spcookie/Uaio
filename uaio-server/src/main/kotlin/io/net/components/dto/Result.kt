package io.net.components.dto

import kotlin.properties.Delegates

class Result<T> {

    var code by Delegates.notNull<Int>()

    var message: String? = null

    var data: T? = null

    val timestamp: Long = System.currentTimeMillis()

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

        fun <T> fail(code: ResultCode, message: String?): Result<T> {
            val result = Result<T>()
            result.code = code.code
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