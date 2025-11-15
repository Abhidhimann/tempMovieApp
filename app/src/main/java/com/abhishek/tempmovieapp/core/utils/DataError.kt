package com.abhishek.tempmovieapp.core.utils

import java.lang.Exception

sealed class DataError: Exception() {
    data class NetworkError(val code: Int, override val message: String? = null): DataError()
    object LimitReached: DataError()
    data class UnknownError(val exception: Throwable): DataError()
}