package com.estebanposada.artischallenge.di

import com.estebanposada.artischallenge.BuildConfig
import com.estebanposada.data.remote.api.ArtistApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideDiscogsInterceptor() = DiscogsAuthInterceptor()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun provideClient(
        interceptor: HttpLoggingInterceptor,
        discogsInterceptor: DiscogsAuthInterceptor
    ) =
        OkHttpClient.Builder().addInterceptor(interceptor)
//            .addInterceptor(discogsInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideArtistApi(client: OkHttpClient, factory: GsonConverterFactory): ArtistApi =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client).addConverterFactory(factory)
            .build().create()
}