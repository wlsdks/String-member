package com.tony.string.controller.response

/**
 * 응답 DTO
 */
class ResponseDTO(
    val success: Boolean,
    val data: Any? = null,
    val error: String? = null,
    val errorCode: String? = null,
    val message: String? = null,
) {
    companion object {
        fun success(data: Any): ResponseDTO {
            return ResponseDTO(success = true, data = data)
        }

        fun success(): ResponseDTO {
            return ResponseDTO(success = true, message = "Success")
        }

        fun error(
            errorCode: String,
            errorMessage: String,
        ): ResponseDTO {
            return ResponseDTO(success = false, error = errorMessage, errorCode = errorCode)
        }
    }
}
