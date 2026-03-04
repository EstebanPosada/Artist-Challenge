package com.estebanposada.artischallenge.di

import com.estebanposada.artischallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class DiscogsAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Discogs token=${BuildConfig.DISCOGS_TOKEN}")
            .addHeader("User-Agent", "ArtistChallengeApp/1.0")
            .build()
        return chain.proceed(request)
    }
}