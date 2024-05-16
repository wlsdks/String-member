package com.tony.string.exception

class CustomException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)
