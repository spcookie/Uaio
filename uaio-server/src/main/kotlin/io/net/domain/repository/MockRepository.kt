package io.net.domain.repository

import io.net.component.domain.ID
import io.net.domain.model.entity.Mock

interface MockRepository {

    fun save(mock: Mock)

    fun removeById(id: ID)

    fun updateById(mock: Mock)

}