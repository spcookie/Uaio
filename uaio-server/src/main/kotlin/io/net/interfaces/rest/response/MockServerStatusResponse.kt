package io.net.interfaces.rest.response

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class MockServerStatusResponse(val status: String)