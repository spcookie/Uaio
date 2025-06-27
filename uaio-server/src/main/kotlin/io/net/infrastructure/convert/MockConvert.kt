package io.net.infrastructure.convert

import io.net.common.enums.HttpArg
import io.net.components.domain.identify
import io.net.domain.model.entity.Mock
import io.net.domain.model.valueobject.MockConfig
import io.net.infrastructure.adapter.db.po.MockPO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
abstract class MockConvert {

    companion object {
        @JvmStatic
        val INSTANCE: MockConvert = Mappers.getMapper(MockConvert::class.java)
    }

    fun toPO(entity: Mock): MockPO {
        return MockPO().apply {
            id = entity.id.value
            method = entity.config.method.name
            path = entity.config.path
            headers = entity.config.headers.associate {
                it.name to it.value
            }
            args = entity.config.args.map {
                when (it) {
                    is MockConfig.PathArg -> "path" to it.name
                    is MockConfig.QueryArg -> "query" to it.name
                }
            }
            template = entity.config.template
        }
    }

    fun toEntity(po: MockPO): Mock {
        return Mock(
            id = po.id.identify,
            config = MockConfig(
                method = MockConfig.Method.valueOf(po.method!!),
                path = po.path!!,
                headers = po.headers?.map {
                    MockConfig.Header(it.key, it.value)
                } ?: listOf(),
                args = po.args?.map {
                    when (it.first) {
                        HttpArg.Query.value -> MockConfig.QueryArg(it.second)
                        HttpArg.Path.value -> MockConfig.PathArg(it.second)
                        else -> throw IllegalArgumentException("unknown arg type: ${it.first}")
                    }
                } ?: listOf(),
                template = po.template!!
            )
        )
    }

}

fun Mock.convert(): MockPO = MockConvert.INSTANCE.toPO(this)

fun MockPO.convert(): Mock = MockConvert.INSTANCE.toEntity(this)