package io.net.interfaces.rest

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.RequestBean
import io.net.application.service.IMockAppService
import io.net.components.domain.PlaceholderID
import io.net.components.dto.Result
import io.net.domain.model.entity.Mock
import io.net.domain.model.valueobject.MockServerConfig
import io.net.domain.service.IMockService
import io.net.interfaces.rest.convert.convert
import io.net.interfaces.rest.param.MockCommandRequest
import io.net.interfaces.rest.param.ServerCommandRequest
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono

@Controller("/mock")
class MockController(
    val mockAppService: IMockAppService,
    val mockService: IMockService
) {

    @Patch("/server/status")
    fun updateServerStatus(@RequestBean command: ServerCommandRequest): Mono<Result<Unit>> {
        return mono {
            mockAppService.updateServerStatus(MockServerConfig(command.open, command.port))
        }.thenReturn<Result<Unit>>(Result.success())
    }

    @Post
    fun addMock(@RequestBean command: MockCommandRequest): Mono<Result<Unit>> {
        return mono {
            mockService.save(Mock(PlaceholderID(), command.convert()))
        }.thenReturn<Result<Unit>>(Result.success())
    }

}