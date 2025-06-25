package io.net.toolkit

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Context

@Context
class AppContextUtils(applicationContext: ApplicationContext) {

    init {
        INSTANCE = applicationContext
    }

    companion object {
        lateinit var INSTANCE: ApplicationContext
    }

}

object BeanUtils {
    inline fun <reified T> getBean(): T {
        return AppContextUtils.INSTANCE.getBean(T::class.java)
    }
}
