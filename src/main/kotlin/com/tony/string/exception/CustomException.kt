package com.tony.string.exception

class CustomException(
    val errorCode: ErrorCode,
    message: String = errorCode.message
) : RuntimeException(message)