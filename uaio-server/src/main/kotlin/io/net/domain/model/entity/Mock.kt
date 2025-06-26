package io.net.domain.model.entity

import io.net.components.domain.Entity
import io.net.components.domain.ID
import io.net.domain.model.valueobject.MockConfig

class Mock(id: ID, val config: MockConfig) : Entity(id)