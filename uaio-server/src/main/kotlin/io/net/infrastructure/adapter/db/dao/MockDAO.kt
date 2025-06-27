package io.net.infrastructure.adapter.db.dao

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import io.net.infrastructure.adapter.db.po.MockPO
import reactor.core.publisher.Flux

@R2dbcRepository(dialect = Dialect.H2)
interface MockDAO : ReactorPageableRepository<MockPO, Long> {
    fun findAllByMethodAndPath(method: String, path: String): Flux<MockPO>

    fun findAllByMethodAndPathAndIdNotEqual(method: String, path: String, id: Long): Flux<MockPO>

}