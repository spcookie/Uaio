package io.net.interfaces.rest.param

import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema

@Serdeable
data class ServerCommandRequest(
    @Schema(description = "是否开启服务")
    val open: Boolean,
    @Schema(description = "服务端口")
    val port: Int
)