package io.net.interfaces.rest.param

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class MockCommandRequest(
    val method: String,
    val path: String,
    val headers: Map<String, List<String>>,
    val args: List<Pair<String, String>>,
    val template: String
)