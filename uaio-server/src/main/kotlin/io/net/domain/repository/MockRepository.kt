package io.net.domain.repository

import io.net.components.domain.ID
import io.net.domain.model.entity.Mock
import kotlinx.coroutines.flow.Flow

interface MockRepository {

    suspend fun save(mock: Mock)

    suspend fun removeById(id: ID)

    suspend fun updateById(mock: Mock)

    suspend fun list(): Flow<Mock>

}