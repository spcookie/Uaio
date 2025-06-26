package io.net.infrastructure.adapter.db.po

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.TypeDef
import io.micronaut.data.model.DataType
import io.net.components.db.PO

@MappedEntity("t_mock")
class MockPO : PO() {

    var method: String? = null

    var path: String? = null

    @TypeDef(type = DataType.JSON)
    var headers: Map<String, List<String>>? = null

    @TypeDef(type = DataType.JSON)
    var args: List<Pair<String, String>>? = null

    var template: String? = null

}