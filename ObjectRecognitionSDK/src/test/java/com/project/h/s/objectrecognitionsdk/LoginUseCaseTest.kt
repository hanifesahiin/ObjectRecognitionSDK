package com.project.h.s.objectrecognitionsdk

import com.project.h.s.objectrecognitionsdk.data.entities.Error
import com.project.h.s.objectrecognitionsdk.data.entities.LoginUserResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.LoginSuccess
import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class LoginUseCaseTest {
    @Test
    fun `test sign in success process`() = runTest {
        val apiCallRepository: ApiCallRepository = Mockito.mock(ApiCallRepository::class.java)
        val successLoginUserModel = LoginUserModel(
            userName = "Michael",
            password = "success-password"
        )
        Mockito.`when`(
            apiCallRepository.login(
                successLoginUserModel
            )
        ).then {
            loginResult(successLoginUserModel)
        }

        val loginUseCase = LoginUseCase(apiCallRepository)
        val actual = loginUseCase.invoke(successLoginUserModel).single()

        Assert.assertEquals(
            actual,
            ApiResponse.success(
                LoginSuccess(
                    message = "message",
                    token = "token"
                )
            )
        )
    }

    @Test
    fun `test sign in fail process`() = runTest {
        val apiCallRepository: ApiCallRepository = Mockito.mock(ApiCallRepository::class.java)
        val failLoginUserModel = LoginUserModel(
            userName = "Michael",
            password = "failed-password"
        )
        Mockito.`when`(
            apiCallRepository.login(
                failLoginUserModel
            )
        ).then {
            loginResult(failLoginUserModel)
        }

        val loginUseCase = LoginUseCase(apiCallRepository)
        val actual = loginUseCase.invoke(failLoginUserModel).single()

        Assert.assertEquals(
            actual,
            ApiResponse.error<String>("sign in" + ": " + "invalid")
        )
    }

    private fun loginResult(loginUserModel: LoginUserModel) = flow {
        if (loginUserModel.password.contains("fail")) {
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
                    success = true,
                    message = "message",
                    token = "token"
                )
            )
        }
    }
}