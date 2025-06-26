package io.net.interfaces.rest.convert

import io.net.domain.model.valueobject.MockConfig
import io.net.interfaces.rest.param.MockCommandRequest
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class MockConvert {

    companion object {
        val INSTANCE: MockConvert = Mappers.getMapper(MockConvert::class.java)
    }

    fun toValueObject(request: MockCommandRequest): MockConfig {
        return MockConfig(
            method = MockConfig.Method.valueOf(request.method),
            path = request.path,
            args = request.args.map {
                when (it.first) {
                    "query" -> MockConfig.QueryArg(it.second)
                    "path" -> MockConfig.PathArg(it.second)
                    else -> throw IllegalArgumentException("参数类型错误")
                }
            },
            headers = request.headers.map {
                MockConfig.Header(it.key, it.value)
            },
            template = request.template
        )
    }
}

fun MockCommandRequest.convert(): MockConfig {
    return MockConvert.INSTANCE.toValueObject(this)
}