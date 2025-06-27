package io.net.interfaces.rest.convert

import io.net.common.enums.HttpArg
import io.net.components.domain.scalar
import io.net.domain.model.entity.Mock
import io.net.domain.model.valueobject.MockConfig
import io.net.interfaces.rest.param.MockCommandRequest
import io.net.interfaces.rest.response.MockResponse
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
                    HttpArg.Query -> MockConfig.QueryArg(it.second)
                    HttpArg.Path -> MockConfig.PathArg(it.second)
                }
            },
            headers = request.headers.map {
                MockConfig.Header(it.key, it.value)
            },
            template = request.template
        )
    }

    fun toResponse(mock: Mock): MockResponse {
        return MockResponse(
            id = mock.id.scalar,
            method = mock.config.method.name,
            path = mock.config.path,
            headers = mock.config.headers.associate {
                it.name to it.value
            },
            args = mock.config.args.map {
                when (it) {
                    is MockConfig.QueryArg -> HttpArg.Query.value to it.name
                    is MockConfig.PathArg -> HttpArg.Query.value to it.name
                }
            },
            template = mock.config.template
        )
    }
}

fun MockCommandRequest.convert(): MockConfig {
    return MockConvert.INSTANCE.toValueObject(this)
}

fun Mock.convert(): MockResponse {
    return MockConvert.INSTANCE.toResponse(this)
}