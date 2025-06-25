package io.net.infrastructure.convert

import io.net.domain.model.entity.Mock
import io.net.infrastructure.adapter.db.po.MockPO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface MockConvert {

    companion object {
        @JvmStatic
        val INSTANCE = Mappers.getMapper(MockConvert::class.java)
    }

    fun toPO(entity: Mock): MockPO

}