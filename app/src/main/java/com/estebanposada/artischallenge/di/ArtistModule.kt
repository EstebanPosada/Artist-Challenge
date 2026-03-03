package com.estebanposada.artischallenge.di

import com.estebanposada.artischallenge.repository.ArtistRepositoryImpl
import com.estebanposada.domain.repository.ArtistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ArtistModule {
    @Binds
    fun bindArtistRepository(impl: ArtistRepositoryImpl): ArtistRepository
}