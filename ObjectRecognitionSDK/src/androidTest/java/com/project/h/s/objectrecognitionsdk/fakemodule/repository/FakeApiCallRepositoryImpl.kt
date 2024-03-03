package com.project.h.s.objectrecognitionsdk.fakemodule.repository

import com.project.h.s.objectrecognitionsdk.data.entities.Error
import com.project.h.s.objectrecognitionsdk.data.entities.LoginUserResponse
import com.project.h.s.objectrecognitionsdk.data.entities.UserItemResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeApiCallRepositoryImpl : ApiCallRepository {
    override fun login(body: LoginUserModel?): Flow<LoginUserResponse> {
        return flow {
            body?.let {
                if (body.password.contains("fail")) {
                    emit(
                        LoginUserResponse(
                            success = false,
                            error = Error(
                                code = "sign in",
                                message = "invalid"
                            )
                        )
                    )
                } else {
                    emit(
                        LoginUserResponse(
                            success = true
                        )
                    )
                }
            }
        }
    }

    override fun allUser(token: String): Flow<ApiResponse<Any>> {
        return flow {
            emit(
                ApiResponse.success(
                    listOf(
                        UserItemResponse(
                            id = 1,
                            name = "Michael",
                            email = "michael@mailinator.com",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        )
                    )
                )
            )
        }
    }
}