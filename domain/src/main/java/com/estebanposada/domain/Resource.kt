package com.estebanposada.domain

import com.estebanposada.domain.Error as DomainError

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val error: DomainError) : Resource<Nothing>()
}
