package ru.virarnd.dogopedia.repository.network

import retrofit2.HttpException
import ru.virarnd.dogopedia.repository.network.helpers.RequestResult
import java.lang.Exception
import java.net.SocketTimeoutException

open class ResponseHandler {

    fun <T: Any> handleSuccess(data: T): RequestResult<T> {
        return RequestResult.Success(data)
    }

    fun <T: Any> handleException(e: Exception): RequestResult<T> {
        return when (e) {
            is HttpException -> RequestResult.Error("${getErrorMessage(e.code())} ${e.response()?.errorBody()?.string()}")
            is SocketTimeoutException -> RequestResult.Error(getErrorMessage(
                ErrorCodes.SocketTimeOut.code))
            else -> RequestResult.Error(getErrorMessage(Int.MAX_VALUE))
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when(code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorized"
            404 -> "Not found"
            422 -> "The given data was invalid"
            else -> "Something went wrong"
        }
    }
}

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

