package io.net.domain.service.impl

import io.net.component.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.model.entity.MockEngine
import io.net.domain.service.IMockService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import jakarta.inject.Singleton

@Singleton
class MockService : IMockService {

    private val mockEngine = MockEngine()

    @PostConstruct
    fun init() {
        mockEngine.start()
    }

    @PreDestroy
    fun destroy() {
        mockEngine.stop()
    }

    override fun generate(mock: Mock): String {
        return mockEngine.mock(mock.config.template)
    }

    override fun save(mock: Mock) {
        TODO("Not yet implemented")
    }

    override fun removeById(id: ID) {
        TODO("Not yet implemented")
    }

    override fun updateById(mock: Mock) {
        TODO("Not yet implemented")
    }

}