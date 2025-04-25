package com.example.kaizenproject.common

import com.example.kaizenproject.common.ui.ErrorType

sealed class CommonResult {

    data object Success : CommonResult()
    data class Error(val error: ErrorType) : CommonResult()
}
