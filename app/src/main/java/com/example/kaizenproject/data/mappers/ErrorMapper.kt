package com.example.kaizenproject.data.mappers

import com.example.kaizenproject.common.ui.ErrorType
import com.example.kaizenproject.common.ui.KaizenProjectError
import com.example.kaizenproject.common.ui.KaizenProjectError.*
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.mapToErrorType(): ErrorType {
    return when (this) {
        is UnknownHostException -> ErrorType(errorType = NetworkOffline, throwable = this)
        is SocketTimeoutException -> ErrorType(errorType = NetworkTimeout, throwable = this)
        is IllegalArgumentException -> ErrorType(errorType = IllegalArgument, throwable = this)
        is HttpException -> ErrorType(errorType = exceptionType(), throwable = this)
        else -> ErrorType(errorType = Unknown, throwable = this)
    }
}

fun HttpException.exceptionType(): KaizenProjectError {
    return when (code()) {
        HttpURLConnection.HTTP_BAD_REQUEST -> BadRequest
        HttpURLConnection.HTTP_UNAUTHORIZED -> Unauthorized
        HttpURLConnection.HTTP_GONE -> Expired
        HttpURLConnection.HTTP_FORBIDDEN -> Forbidden
        else -> Unknown
    }
}
