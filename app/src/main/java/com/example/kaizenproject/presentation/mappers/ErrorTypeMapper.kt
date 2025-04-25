package com.example.kaizenproject.presentation.mappers

import android.content.Context
import com.example.kaizenproject.R
import com.example.kaizenproject.common.ui.KaizenProjectError
import com.example.kaizenproject.common.ui.KaizenProjectError.*
import javax.inject.Inject

class ErrorTypeMapper @Inject constructor(private val context: Context) {

    fun mapError(error: KaizenProjectError?): String? {
        if (error == null) return null
        return when (error) {
            is NetworkOffline -> context.getString(R.string.error_network_offline)
            is NetworkTimeout -> context.getString(R.string.error_network_timeout)
            is IllegalArgument -> context.getString(R.string.error_illegal_argument)
            is BadRequest -> context.getString(R.string.error_bad_request)
            is Forbidden -> context.getString(R.string.error_forbidden)
            is Unauthorized -> context.getString(R.string.error_unauthorized)
            is Expired -> context.getString(R.string.error_expired)
            else -> {
                context.getString(R.string.error_unknown)
            }
        }
    }
}
