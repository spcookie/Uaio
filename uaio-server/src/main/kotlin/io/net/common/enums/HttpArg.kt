package io.net.common.enums

import com.fasterxml.jackson.annotation.JsonValue
import io.micronaut.serde.annotation.Serdeable

@Serdeable
enum class HttpArg(
    @JsonValue
    val value: String
) {
    Query("query"),
    Path("path")
}