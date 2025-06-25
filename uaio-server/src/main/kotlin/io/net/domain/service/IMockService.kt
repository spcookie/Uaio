package io.net.domain.service

import io.net.component.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.model.valueobject.MockServerConfig

interface IMockService {

    suspend fun generate(mock: Mock): String

    suspend fun save(mock: Mock)

    suspend fun removeById(id: ID)

    suspend fun updateById(mock: Mock)

    suspend fun updateServerConfig(config: MockServerConfig)

}