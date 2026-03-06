package com.estebanposada.artischallenge.ui.common

import com.estebanposada.domain.Error

sealed class UiError {
    object Network : UiError()
    object NotFound : UiError()
    object Unknown : UiError()
    object RequestLimit : UiError()
    object Unauthorized : UiError()
}

fun Error.toUiError() = when (this) {
    Error.Network -> UiError.Network
    Error.NotFound -> UiError.NotFound
    Error.Unknown -> UiError.Unknown
    Error.RequestLimit -> UiError.RequestLimit
    Error.Unauthorized -> UiError.Unauthorized
}
