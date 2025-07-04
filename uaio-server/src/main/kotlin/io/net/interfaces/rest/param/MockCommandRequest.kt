package io.net.interfaces.rest.param

import io.micronaut.serde.annotation.Serdeable
import io.net.common.enums.HttpArg
import io.swagger.v3.oas.annotations.media.Schema

@Serdeable
data class MockCommandRequest(
    @Schema(description = "请求方法")
    val method: String,
    @Schema(description = "请求路径")
    val path: String,
    @Schema(description = "请求头")
    val headers: Map<String, List<String>>,
    @Schema(description = "请求参数")
    val args: List<Pair<HttpArg, String>>,
    @Schema(description = "模板")
    val template: String
)