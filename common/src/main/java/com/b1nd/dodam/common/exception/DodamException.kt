package com.b1nd.dodam.common.exception

class BadRequestException(
    override val message: String?
) : RuntimeException()

class UnauthorizedException(
    override val message: String?
) : RuntimeException()

class ForbiddenException(
    override val message: String?
) : RuntimeException()

class NotFoundException(
    override val message: String?
) : RuntimeException()

class ConflictException(
    override val message: String?
) : RuntimeException()

class TooManyRequestsException(
    override val message: String?
) : RuntimeException()

class InternalServerException(
    override val message: String?,
    val status: Int
) : RuntimeException()

class DataNotFoundException(
    override val message: String?,
) : RuntimeException()
