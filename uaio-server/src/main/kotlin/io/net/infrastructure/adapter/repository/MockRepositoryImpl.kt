package io.net.infrastructure.adapter.repository

import io.net.components.domain.ID
import io.net.domain.model.entity.Mock
import io.net.domain.repository.MockRepository
import io.net.infrastructure.adapter.db.dao.MockDAO
import io.net.infrastructure.convert.MockConvert
import io.net.infrastructure.convert.convert
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle

@Singleton
class MockRepositoryImpl(val mockDAO: MockDAO) : MockRepository {
    override suspend fun save(mock: Mock) {
        mockDAO.save(mock.convert()).awaitSingle()
    }

    override suspend fun removeById(id: ID) {
        mockDAO.findById(id.value).awaitSingle()
    }

    override suspend fun updateById(mock: Mock) {
        val po = MockConvert.INSTANCE.toPO(mock)
        mockDAO.update(po).awaitSingle()
    }

    override suspend fun list(): Flow<Mock> {
        return mockDAO.findAll()
            .map { po -> po.convert() }
            .asFlow()
    }
}