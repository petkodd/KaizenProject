package com.example.kaizenproject.common.ui

abstract class KaizenProjectError {

    data object NetworkOffline : KaizenProjectError()

    data object NetworkTimeout : KaizenProjectError()

    data object IllegalArgument : KaizenProjectError()

    data object BadRequest : KaizenProjectError()

    data object Forbidden : KaizenProjectError()

    data object Unauthorized : KaizenProjectError()

    data object Expired : KaizenProjectError()

    data object Unknown : KaizenProjectError()
}
