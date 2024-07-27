package com.b1nd.dodam.core

import com.b1nd.dodam.common.exception.BadRequestException
import com.b1nd.dodam.common.exception.ConflictException
import com.b1nd.dodam.common.exception.DataNotFoundException
import com.b1nd.dodam.common.exception.ForbiddenException
import com.b1nd.dodam.common.exception.InternalServerException
import com.b1nd.dodam.common.exception.LockedException
import com.b1nd.dodam.common.exception.NotFoundException
import com.b1nd.dodam.common.exception.TooManyRequestsException
import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SafeRequestTest {


    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun 성공적인_요청_테스트() = runTest(testDispatcher) {
        val mockResponse = Response(
            status = 200,
            data = "Success",
            message = "OK"
        )

        val result = safeRequest { mockResponse }
        assertEquals("Success", result)
    }

    @Test
    fun 데이터_누락_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 200,
            data = null,
            message = "No data"
        )
        assertFailsWith<DataNotFoundException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 베드리퀘스트_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 400,
            data = null,
            message = "Bad Request"
        )
        assertFailsWith<BadRequestException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 인증_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 401,
            data = null,
            message = "Unauthorized"
        )
        assertFailsWith<UnauthorizedException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 금지된_요청_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 403,
            data = null,
            message = "Forbidden"
        )
        assertFailsWith<ForbiddenException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 찾을_수_없음_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 404,
            data = null,
            message = "Not Found"
        )
        assertFailsWith<NotFoundException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 충돌_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 409,
            data = null,
            message = "Conflict"
        )
        assertFailsWith<ConflictException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 잠금_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 423,
            data = null,
            message = "Locked"
        )
        assertFailsWith<LockedException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 너무_많은_요청_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 429,
            data = null,
            message = "Too Many Requests"
        )
        assertFailsWith<TooManyRequestsException> {
            safeRequest { mockResponse }
        }
    }

    @Test
    fun 내부_서버_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = Response<Unit>(
            status = 500,
            data = null,
            message = "Internal Server Error"
        )
        assertFailsWith<InternalServerException> {
            safeRequest { mockResponse }
        }
    }




}