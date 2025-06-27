package io.net.components.domain

import cn.hutool.core.util.IdUtil

open class ID internal constructor(val value: Long?) {
    companion object
}

class RandomID internal constructor() : ID(IdUtil.getSnowflakeNextId())

object PlaceholderID : ID(null)

val GlobalUniqueID
    get() = RandomID()

fun ID.Companion.from(value: Long): ID {
    return ID(value)
}

fun ID.Companion.into(id: ID): Long {
    if (id is PlaceholderID) {
        throw IllegalArgumentException("id cannot be empty")
    } else {
        return id.value!!
    }
}

val Long?.identify: ID
    get() {
        return if (this == null) {
            PlaceholderID
        } else {
            ID.from(this)
        }
    }

val ID.scalar: Long
    get() = ID.into(this)