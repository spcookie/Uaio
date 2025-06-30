package io.net.domain.model.valueobject

data class MockTreeConfig(
    val method: Method,
    val path: String,
    val headers: List<Header>,
    val args: List<Arg>,
    val template: String,
    val children: List<MockTreeConfig>
)