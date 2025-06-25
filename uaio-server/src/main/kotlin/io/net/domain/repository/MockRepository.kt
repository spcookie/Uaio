package io.net.domain.repository

import io.net.component.domain.ID
import io.net.domain.model.entity.Mock

interface MockRepository {

    suspend fun save(mock: Mock)

    suspend fun removeById(id: ID)

    suspend fun updateById(mock: Mock)

}