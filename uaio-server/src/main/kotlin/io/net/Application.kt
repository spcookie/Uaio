package io.net

import io.micronaut.context.ApplicationContextBuilder
import io.micronaut.context.ApplicationContextConfigurer
import io.micronaut.context.annotation.ContextConfigurer
import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "uaio-server",
        version = "0.1"
    )
)
object Api

@ContextConfigurer
class Configurer : ApplicationContextConfigurer {
    override fun configure(builder: ApplicationContextBuilder) {
        builder.defaultEnvironments("dev");
        builder.eagerInitSingletons(true);
    }
}

fun main(args: Array<String>) {
    run(*args)
}

