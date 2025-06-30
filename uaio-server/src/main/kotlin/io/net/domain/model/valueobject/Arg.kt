package io.net.domain.model.valueobject

sealed interface Arg {
    val name: String
}

data class QueryArg(
    override val name: String,
) : Arg

data class PathArg(
    override val name: String,
) : Arg