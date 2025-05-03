package com.b1nd.dodam.network.core.util

import com.b1nd.dodam.common.exception.BadRequestException
import com.b1nd.dodam.common.exception.ConflictException
import com.b1nd.dodam.common.exception.DataNotFoundException
import com.b1nd.dodam.common.exception.ForbiddenException
import com.b1nd.dodam.common.exception.InternalServerException
import com.b1nd.dodam.common.exception.LockedException
import com.b1nd.dodam.common.exception.NotFoundException
import com.b1nd.dodam.common.exception.TooManyRequestsException
import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response

suspend inline fun <T> safeRequest(crossinline request: suspend () -> Response<T>): T {
    val response = request()
    return when (response.status) {
        200, 201, 204 -> response.data ?: throw DataNotFoundException(response.message)
        400 -> throw BadRequestException(response.message)
        401 -> throw UnauthorizedException(response.message)
        403 -> throw ForbiddenException(response.message)
        404 -> throw NotFoundException(response.message)
        409 -> throw ConflictException(response.message)
        423 -> throw LockedException(response.message)
        429 -> throw TooManyRequestsException(response.message)
        500 -> throw InternalServerException(response.message, response.status)
        else -> throw Exception(response.message)
    }
}

suspend inline fun defaultSafeRequest(crossinline request: suspend () -> DefaultResponse) {
    val response = request()
    return when (response.status) {
        200, 201, 204 -> Unit
        400 -> throw BadRequestException(response.message)
        401 -> throw UnauthorizedException(response.message)
        403 -> throw ForbiddenException(response.message)
        404 -> throw NotFoundException(response.message)
        409 -> throw ConflictException(response.message)
        423 -> throw LockedException(response.message)
        429 -> throw TooManyRequestsException(response.message)
        500 -> throw InternalServerException(response.message, response.status)
        else -> throw Exception(response.message)
    }
}


