package com.tony.string.domain.dto

class ApiResponse(
    val success: Boolean,
    val data: Any? = null,
    val error: String? = null,
    val errorCode: String? = null
) {

    companion object {
        fun success(data: Any): ApiResponse {
            return ApiResponse(success = true, data = data)
        }

        fun error(errorCode: String, errorMessage: String): ApiResponse {
            return ApiResponse(success = false, error = errorMessage, errorCode = errorCode)
        }
    }

}