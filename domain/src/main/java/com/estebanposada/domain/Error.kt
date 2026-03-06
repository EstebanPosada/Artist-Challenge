package com.estebanposada.domain

sealed class Error {
    object Network : Error()
    object NotFound : Error()
    object Unauthorized : Error()
    object RequestLimit : Error()
    object Unknown : Error()
}
