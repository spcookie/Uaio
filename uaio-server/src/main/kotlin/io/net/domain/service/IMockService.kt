package io.net.domain.service

import io.net.component.domain.ID
import io.net.domain.model.entity.Mock

interface IMockService {

    fun generate(mock: Mock): String

    fun save(mock: Mock)

    fun removeById(id: ID)

    fun updateById(mock: Mock)

}