package io.net.infrastructure.adapter.repository

import io.net.component.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.repository.MockRepository
import io.net.infrastructure.adapter.db.dao.MockDAO
import io.net.infrastructure.adapter.db.po.MockPO
import io.net.infrastructure.convert.MockConvert
import jakarta.inject.Singleton
import kotlinx.coroutines.reactor.awaitSingle

@Singleton
class MockRepositoryImpl(val mockDAO: MockDAO) : MockRepository {
    override suspend fun save(mock: Mock) {
        val po = MockConvert.INSTANCE.toPO(mock)
        mockDAO.save<MockPO>(po).awaitSingle()
    }

    override suspend fun removeById(id: ID) {
        mockDAO.findById(id.value).awaitSingle()
    }

    override suspend fun updateById(mock: Mock) {
        val po = MockConvert.INSTANCE.toPO(mock)
        mockDAO.update<MockPO>(po).awaitSingle()
    }
}