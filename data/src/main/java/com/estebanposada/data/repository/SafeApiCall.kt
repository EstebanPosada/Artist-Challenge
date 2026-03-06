package com.estebanposada.data.repository

import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource
import okio.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> = try {
    Resource.Success(apiCall())
} catch (_: IOException) {
    Resource.Error(Error.Network)
} catch (e: HttpException) {
    when (e.code()) {
        404 -> Resource.Error(Error.NotFound)
        401 -> Resource.Error(Error.Unauthorized)
        429 -> Resource.Error(Error.RequestLimit)
        else -> Resource.Error(Error.Unknown)
    }
} catch (_: Exception) {
    Resource.Error(Error.Unknown)
}
