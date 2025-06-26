package io.net.components.dto

interface ResultCode {

    val code: Int

    val message: String

    object Success : ResultCode {
        override val code = 20000
        override val message = "操作成功"
    }

    object Fail : ResultCode {
        override val code = 50000
        override val message = "操作失败"
    }

    object NotFound : ResultCode {
        override val code = 40400
        override val message = "未找到该资源"
    }

    object Unauthorized : ResultCode {
        override val code = 40100
        override val message = "未授权"
    }

    object Forbidden : ResultCode {
        override val code = 40300
        override val message = "禁止访问"
    }

}