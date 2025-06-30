package io.net.application.service.impl

import io.net.application.service.IMockAppService
import io.net.domain.model.valueobject.MockServerConfig
import io.net.domain.service.IMockService
import jakarta.inject.Singleton

@Singleton
class MockAppService(
    val mockService: IMockService
) : IMockAppService {

    override suspend fun updateServerStatus(config: MockServerConfig) {
        if (config.open) {
            mockService.startServer(config)
        } else {
            mockService.stopServer(config)
        }
    }

    override suspend fun listTree() {
        val mocks = mockService.list()
        TODO("Not yet implemented")
    }

}