package com.project.h.s.objectrecognitionsdk.domain.usecases

import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.LoginSuccess
import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

class LoginUseCase(private val apiCallRepository: ApiCallRepository) {
    suspend operator fun invoke(loginUserModel: LoginUserModel): Flow<ApiResponse<Any>> {
        val result = apiCallRepository.login(
            loginUserModel
        ).single()

        return flow {
            if (result.success) {
                emit(ApiResponse.success(LoginSuccess(
                    message = result.message,
                    token = result.token
                )))
            } else {
                emit(ApiResponse.error(result.error.code + ": " + result.error.message))
            }
        }
    }
}