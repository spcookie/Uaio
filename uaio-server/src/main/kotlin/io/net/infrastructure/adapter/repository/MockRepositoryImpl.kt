package io.net.infrastructure.adapter.repository

import io.net.component.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.repository.MockRepository
import io.net.infrastructure.adapter.db.dao.MockDAO
import io.net.infrastructure.adapter.db.po.MockPO
import jakarta.inject.Singleton

@Singleton
class MockRepositoryImpl(val mockDAO: MockDAO) : MockRepository {
    override fun save(mock: Mock) {
        mockDAO.save<MockPO>(null)
        TODO("Not yet implemented")
    }

    override fun removeById(id: ID) {
        TODO("Not yet implemented")
    }

    override fun updateById(mock: Mock) {
        TODO("Not yet implemented")
    }
}