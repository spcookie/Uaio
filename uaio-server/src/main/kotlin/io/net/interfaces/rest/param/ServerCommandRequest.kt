package io.net.interfaces.rest.param

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ServerCommandRequest(
    val open: Boolean,
    val port: Int
)