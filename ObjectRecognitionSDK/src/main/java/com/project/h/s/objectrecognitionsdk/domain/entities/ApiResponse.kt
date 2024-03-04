package com.project.h.s.objectrecognitionsdk.domain.entities

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val errorMessage: String) : ApiResponse<T>()

    companion object {
        fun <T> success(data: T): ApiResponse<T> = Success(data)
        fun <T> error(errorMessage: String): ApiResponse<T> = Error(errorMessage)
    }
}