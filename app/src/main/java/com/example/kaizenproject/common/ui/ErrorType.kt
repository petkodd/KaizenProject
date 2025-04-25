package com.example.kaizenproject.common.ui

data class ErrorType(
    val errorType: KaizenProjectError,
    val throwable: Throwable,
)
