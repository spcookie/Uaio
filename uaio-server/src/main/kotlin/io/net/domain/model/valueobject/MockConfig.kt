package io.net.domain.model.valueobject

data class MockConfig(
    val method: Method,
    val path: String,
    val headers: List<Header>,
    val args: List<Arg>,
    val template: String
) {
    enum class Method {
        GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS
    }

    sealed interface Arg {
        val name: String
    }

    data class QueryArg(
        override val name: String,
    ) : Arg

    data class PathArg(
        override val name: String,
    ) : Arg

    data class Header(
        val name: String,
        val value: List<String>
    )
}