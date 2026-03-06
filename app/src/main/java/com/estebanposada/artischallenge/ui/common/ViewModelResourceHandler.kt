package com.estebanposada.artischallenge.ui.common

import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onFailure(action: (Error) -> Unit): Resource<T> {
    if (this is Resource.Error) action(error)
    return this
}
