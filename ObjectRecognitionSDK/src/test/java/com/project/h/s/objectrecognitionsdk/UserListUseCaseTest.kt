package com.project.h.s.objectrecognitionsdk

import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
import com.project.h.s.objectrecognitionsdk.data.entities.UserItemResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.UserItem
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.domain.usecases.UserListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class UserListUseCaseTest {
    @Test
    fun `test user list success process`() = runTest {
        val apiCallRepository: ApiCallRepository = Mockito.mock(ApiCallRepository::class.java)
        val userSharedPreference: UserSharedPreference =
            Mockito.mock(UserSharedPreference::class.java)

        Mockito.`when`(
            userSharedPreference.getToken()
        ).thenReturn("")

        Mockito.`when`(
            apiCallRepository.allUser(
                ""
            )
        ).then {
            allUser()
        }

        val userListUseCase = UserListUseCase(apiCallRepository, userSharedPreference)
        val actual = userListUseCase.invoke().single()

        Assert.assertEquals(
            actual, listOf(
                UserItem(
                    id = 1,
                    name = "Michael",
                    email = "michael@mailinator.com"
                )
            )
        )
    }

    @Test
    fun `test user list error process`() = runTest {
        val apiCallRepository: ApiCallRepository = Mockito.mock(ApiCallRepository::class.java)
        val userSharedPreference: UserSharedPreference =
            Mockito.mock(UserSharedPreference::class.java)

        Mockito.`when`(
            userSharedPreference.getToken()
        ).thenReturn("")

        Mockito.`when`(
            apiCallRepository.allUser(
                ""
            )
        ).then {
            allUserError()
        }

        val userListUseCase = UserListUseCase(apiCallRepository, userSharedPreference)
        val actual = userListUseCase.invoke().single()

        Assert.assertEquals(
            actual, emptyList<UserItem>()
        )
    }

    private fun allUser(): Flow<ApiResponse<Any>> = flow {
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

    private fun allUserError(): Flow<ApiResponse<Any>> = flow {
        emit(ApiResponse.error("something went wrong"))
    }
}
