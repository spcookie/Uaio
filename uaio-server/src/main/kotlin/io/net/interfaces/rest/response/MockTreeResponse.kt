package io.net.interfaces.rest.response

import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema

@Serdeable
data class MockTreeResponse(
    @Schema(description = "ID")
    val id: Long?,
    @Schema(description = "请求方法")
    val method: String?,
    @Schema(description = "请求路径")
    val path: String?,
    @Schema(description = "请求头")
    val headers: Map<String, List<String>>?,
    @Schema(description = "请求参数")
    val args: List<Pair<String, String>>?,
    @Schema(description = "模板")
    val template: String?,
    @Schema(description = "子节点")
    val children: List<MockTreeResponse>,
)