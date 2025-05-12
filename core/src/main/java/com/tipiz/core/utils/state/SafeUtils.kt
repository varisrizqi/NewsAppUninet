package com.tipiz.core.utils.state

import com.tipiz.core.utils.Contant.ErrorMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineContext = Dispatchers.IO,
    apiCall: suspend () -> T?
): T {
    return withContext(dispatcher) {
        try {
            apiCall() ?: throw Exception("Network null")
        } catch (error: Throwable) {
            when (error) {
                is IOException -> {
                    throw NetworkException("Network error occurred: ${error.message}")
                }
                is HttpException -> {
                    val code = error.code()
                    val errorBody =
                        error.response()?.errorBody()?.string()

                    when (code) {
                        400 -> throw BadRequestException("${ErrorMessages.BAD_REQUEST}: $errorBody")
                        401 -> throw UnauthorizedException("${ErrorMessages.UNAUTHORIZED}: $errorBody")
                        403 -> throw ForbiddenException("${ErrorMessages.FORBIDDEN}: $errorBody")
                        404 -> throw NotFoundException("${ErrorMessages.NOT_FOUND}: $errorBody")
                        500 -> throw InternalServerErrorException("${ErrorMessages.INTERNAL_SERVER_ERROR}: $errorBody")
                        else -> throw ApiException("${ErrorMessages.HTTP_ERROR_OCCURRED}: $errorBody")
                    }
                }
                else -> {
                    throw error
                }
            }
        }
    }
}

class NetworkException(message: String) : Exception(message)
class ApiException(message: String) : Exception(message)
class BadRequestException(message: String) : Exception(message)
class UnauthorizedException(message: String) : Exception(message)
class ForbiddenException(message: String) : Exception(message)
class NotFoundException(message: String) : Exception(message)
class InternalServerErrorException(message: String) : Exception(message)


suspend fun <T> MutableStateFlow<UiState<T>>.asMutableStateFlow(
    dataCall: suspend () -> T
) {
    this.update { UiState.Loading }
    try {
        val data = dataCall.invoke()
        this.update { UiState.Success(data) }
    } catch (error: Throwable) {
        this.update { UiState.Error(error) }
    }
}