package io.net.domain.model.valueobject

data class MockTreeConfig(
    val id: Long? = null,
    val method: Method? = null,
    val path: String,
    val headers: List<Header>? = null,
    val args: List<Arg>? = null,
    val template: String? = null,
    val children: MutableList<MockTreeConfig>,
)