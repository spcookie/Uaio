package io.net.component.domain

import cn.hutool.core.util.IdUtil

open class ID(val value: Long) {
    companion object
}

class GlobalUniqueID : ID(IdUtil.getSnowflakeNextId())

fun ID.Companion.from(value: Long): ID {
    return ID(value)
}

fun ID.Companion.into(id: ID): Long {
    return id.value
}