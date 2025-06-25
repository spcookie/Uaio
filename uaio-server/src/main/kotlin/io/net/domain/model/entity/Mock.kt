package io.net.domain.model.entity

import io.net.component.domain.Entity
import io.net.component.domain.ID
import io.net.domain.model.valueobject.MockConfig

class Mock(id: ID, val config: MockConfig) : Entity(id)