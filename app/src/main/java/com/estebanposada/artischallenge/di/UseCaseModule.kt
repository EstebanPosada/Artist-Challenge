package com.estebanposada.artischallenge.di

import com.estebanposada.domain.repository.ArtistRepository
import com.estebanposada.domain.usecase.GetAlbumInfoUseCase
import com.estebanposada.domain.usecase.GetArtistDetailUseCase
import com.estebanposada.domain.usecase.SearchAlbumUseCase
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
    fun provideGetAlbumByArtistIdUseCase(repository: ArtistRepository) =
        GetArtistDetailUseCase(repository)

    @Provides
    fun provideSearchAlbumUseCase(repository: ArtistRepository) = SearchAlbumUseCase(repository)

    @Provides
    fun provideGetAlbumInfoUseCase(repository: ArtistRepository) = GetAlbumInfoUseCase(repository)
}