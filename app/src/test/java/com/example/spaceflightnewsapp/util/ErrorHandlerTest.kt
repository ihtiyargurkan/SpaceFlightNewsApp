package com.example.spaceflightnewsapp.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandlerTest {

    @Test
    fun getErrorMessage_unknownHostException_returnsNetworkMessage() {
        val exception = UnknownHostException("Unable to resolve host")
        val result = ErrorHandler.getErrorMessage(exception)
        assertEquals("No internet connection. Please check your network.", result)
    }

    @Test
    fun getErrorMessage_socketTimeoutException_returnsTimeoutMessage() {
        val exception = SocketTimeoutException("Read timed out")
        val result = ErrorHandler.getErrorMessage(exception)
        assertEquals("Connection timed out. Please try again.", result)
    }

    @Test
    fun getErrorMessage_ioException_returnsNetworkErrorMessage() {
        val exception = IOException("Connection refused")
        val result = ErrorHandler.getErrorMessage(exception)
        assertEquals("Network error. Please try again.", result)
    }

    @Test
    fun getErrorMessage_genericException_returnsExceptionMessage() {
        val exception = RuntimeException("Custom error message")
        val result = ErrorHandler.getErrorMessage(exception)
        assertEquals("Custom error message", result)
    }

    @Test
    fun getErrorMessage_exceptionWithNullMessage_returnsDefaultMessage() {
        val exception = RuntimeException()
        val result = ErrorHandler.getErrorMessage(exception)
        assertEquals("Something went wrong. Please try again.", result)
    }
}
