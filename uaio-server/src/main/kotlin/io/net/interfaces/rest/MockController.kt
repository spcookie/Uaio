package io.net.interfaces.rest

import io.micronaut.http.annotation.*
import io.net.application.service.IMockAppService
import io.net.components.domain.PlaceholderID
import io.net.components.domain.identify
import io.net.components.dto.Result
import io.net.domain.model.entity.Mock
import io.net.domain.model.valueobject.MockServerConfig
import io.net.domain.service.IMockService
import io.net.interfaces.rest.MockController.Companion.OPENAPI_TAG
import io.net.interfaces.rest.convert.convert
import io.net.interfaces.rest.param.MockCommandRequest
import io.net.interfaces.rest.param.ServerCommandRequest
import io.net.interfaces.rest.response.MockResponse
import io.net.interfaces.rest.response.MockServerStatusResponse
import io.net.interfaces.rest.response.MockTreeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono

@Tag(name = OPENAPI_TAG)
@Controller("/mock")
class MockController(
    val mockAppService: IMockAppService,
    val mockService: IMockService
) {

    companion object {
        const val OPENAPI_TAG = "Mock 服务"
    }

    @Operation(tags = [OPENAPI_TAG], summary = "更新 Mock 服务状态")
    @Patch("/server/status")
    fun updateServerStatus(@RequestBean command: ServerCommandRequest): Mono<Result<Unit>> {
        return mono {
            mockAppService.updateServerStatus(MockServerConfig(command.open, command.port))
        }.thenReturn<Result<Unit>>(Result.success())
    }

    @Operation(tags = [OPENAPI_TAG], summary = "获取 Mock 服务状态")
    @Get("/server/status")
    fun getServerStatus(): Mono<Result<MockServerStatusResponse>> {
        return mono {
            val statue = mockService.getServerStats()
            Result.success(MockServerStatusResponse(statue?.name?.lowercase() ?: "unknown"))
        }
    }

    @Operation(tags = [OPENAPI_TAG], summary = "添加 Mock 模板")
    @Post
    fun addMock(@RequestBean command: MockCommandRequest): Mono<Result<Unit>> {
        return mono {
            mockService.save(Mock(PlaceholderID, command.convert()))
        }.thenReturn<Result<Unit>>(Result.success())
    }

    @Operation(tags = [OPENAPI_TAG], summary = "获取 Mock 列表")
    @Get
    fun list(): Mono<Result<List<MockResponse>>> {
        return mono {
            Result.success(mockService.list().map { it.convert() }.toList())
        }
    }

    @Operation(tags = [OPENAPI_TAG], summary = "更新 Mock 模板")
    @Put("/{id}")
    fun updateMock(@PathVariable id: Long, @RequestBean command: MockCommandRequest): Mono<Result<Unit>> {
        return mono {
            mockService.updateById(Mock(id.identify, command.convert()))
        }.thenReturn<Result<Unit>>(Result.success())
    }

    @Operation(tags = [OPENAPI_TAG], summary = "删除 Mock 模板")
    @Delete("/{id}")
    fun removeMock(@PathVariable id: Long): Mono<Result<Unit>> {
        return mono {
            mockService.removeById(id.identify)
        }.thenReturn<Result<Unit>>(Result.success())
    }

    @Operation(tags = [OPENAPI_TAG], summary = "获取 Mock 树形列表")
    @Get("/tree")
    fun listTree(): Mono<Result<MockTreeResponse>> {
        return mono {
            Result.success(mockService.listTree().convert())
        }
    }

}