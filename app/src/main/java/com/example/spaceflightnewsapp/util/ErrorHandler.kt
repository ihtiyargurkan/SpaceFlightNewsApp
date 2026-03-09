package com.example.spaceflightnewsapp.util

import java.net.UnknownHostException
import java.net.SocketTimeoutException
import java.io.IOException

/**
 * Maps exceptions to user-friendly error messages.
 */
object ErrorHandler {

    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> "No internet connection. Please check your network."
            is SocketTimeoutException -> "Connection timed out. Please try again."
            is IOException -> "Network error. Please try again."
            else -> throwable.message ?: "Something went wrong. Please try again."
        }
    }
}
