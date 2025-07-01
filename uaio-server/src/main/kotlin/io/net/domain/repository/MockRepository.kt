package io.net.domain.repository

import io.net.components.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.model.valueobject.Method
import kotlinx.coroutines.flow.Flow

interface MockRepository {

    suspend fun save(mock: Mock)

    suspend fun removeById(id: ID)

    suspend fun updateById(mock: Mock)

    suspend fun list(): Flow<Mock>

    suspend fun findByMethodAndPath(method: Method, path: String): Flow<Mock>

    suspend fun findAllByMethodAndPathAndIdNotEqual(method: Method, path: String, id: ID): Flow<Mock>

}