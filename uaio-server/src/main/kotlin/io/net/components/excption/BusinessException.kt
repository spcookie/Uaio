package io.net.components.excption

import io.net.components.dto.ResultCode

class BusinessException(
    val code: Int = ResultCode.Fail.code,
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException()