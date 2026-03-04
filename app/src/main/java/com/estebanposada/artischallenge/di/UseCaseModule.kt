package com.estebanposada.artischallenge.di

import com.estebanposada.domain.repository.ArtistRepository
import com.estebanposada.domain.usecase.GetArtistByIdUseCase
import com.estebanposada.domain.usecase.SearchArtistsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideSearchArtistUseCase(repository: ArtistRepository) = SearchArtistsUseCase(repository)

    @Provides
    fun provideGetArtistUseCase(repository: ArtistRepository) = GetArtistByIdUseCase(repository)
}