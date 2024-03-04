package com.project.h.s.objectrecognitionsdk.data.repository

import com.google.gson.Gson
import com.project.h.s.objectrecognitionsdk.data.api.JsonPlaceHolderApiCall
import com.project.h.s.objectrecognitionsdk.data.entities.Error
import com.project.h.s.objectrecognitionsdk.data.entities.LoginUserResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.utils.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class ApiCallRepositoryImpl @Inject constructor(private val jsonPlaceHolderApiCall: JsonPlaceHolderApiCall) :
    ApiCallRepository {
    private val _apiCallRepositoryImpl = "ApiCallRepositoryImpl"
    override fun login(body: LoginUserModel?): Flow<LoginUserResponse> {
        return flow {
            body?.let {
                lateinit var response: Response<LoginUserResponse>
                try {
                    response = jsonPlaceHolderApiCall.login(it)

                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let { emit(it) }
                    } else {
                        val result = response.errorBody()?.byteStream()
                        result?.let {
                            val jsonString = inputStreamToJson(result)
                            if (jsonString.isNotEmpty()) {
                                emit(Gson().fromJson(jsonString, LoginUserResponse::class.java))
                            }
                        }
                    }
                } catch (e: Exception) {
                    Logger.e(_apiCallRepositoryImpl, "login", e)
                    emit(
                        LoginUserResponse(
                            success = false,
                            error = Error(
                                code = "unknown",
                                message = "something went wrong"
                            )
                        )
                    )
                }
            }
        }
    }

    override fun allUser(token: String): Flow<ApiResponse<Any>> {
        return flow {
            try {
                val response = jsonPlaceHolderApiCall.allUser(token)
                emit(ApiResponse.success(response))
            } catch (e: Exception) {
                Logger.e(_apiCallRepositoryImpl, "allUser", e)
                emit(ApiResponse.error("something went wrong"))
            }
        }
    }

    private fun inputStreamToJson(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        reader.close()
        return stringBuilder.toString()
    }
}