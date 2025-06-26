package io.net.components.db

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import java.time.LocalDateTime

open class PO {
    @Id
    @GeneratedValue()
    var id: Long? = null

    @DateCreated
    var createdAt: LocalDateTime? = null

    @DateUpdated
    var updatedAt: LocalDateTime? = null
}