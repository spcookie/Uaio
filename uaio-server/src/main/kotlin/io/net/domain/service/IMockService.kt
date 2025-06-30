package io.net.domain.service

import io.net.components.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.model.entity.MockServer
import io.net.domain.model.valueobject.MockServerConfig
import kotlinx.coroutines.flow.Flow

interface IMockService {

    suspend fun generate(template: String): String

    suspend fun save(mock: Mock)

    suspend fun list(): Flow<Mock>

    suspend fun removeById(id: ID)

    suspend fun updateById(mock: Mock)

    suspend fun refreshServerConfig(config: MockServerConfig)

    suspend fun startServer(config: MockServerConfig): Boolean

    suspend fun stopServer(config: MockServerConfig)

    suspend fun getServerStats(): MockServer.Statue?

    suspend fun listTree(): Flow<Mock>

}