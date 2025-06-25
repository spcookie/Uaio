package io.net.infrastructure.adapter.db.dao

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import io.net.infrastructure.adapter.db.po.MockPO

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface MockDAO : ReactorPageableRepository<MockPO, Long>