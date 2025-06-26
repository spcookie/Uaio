package io.net.application.service

import io.net.domain.model.valueobject.MockServerConfig

interface IMockAppService {
    suspend fun updateServerStatus(config: MockServerConfig)
}