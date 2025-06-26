package io.net.components.domain

import cn.hutool.core.util.IdUtil

open class ID internal constructor(val value: Long?) {
    companion object
}

class GlobalUniqueID : ID(IdUtil.getSnowflakeNextId())

class PlaceholderID : ID(null)

fun ID.Companion.from(value: Long): ID {
    return ID(value)
}

fun ID.Companion.into(id: ID): Long {
    if (id is PlaceholderID) {
        throw IllegalArgumentException("PlaceholderID can not be converted to Long")
    } else {
        return id.value!!
    }
}

val Long?.identify: ID
    get() {
        return if (this == null) {
            PlaceholderID()
        } else {
            ID.from(this)
        }
    }

val ID.scalar: Long
    get() = ID.into(this)