package com.estebanposada.domain.usecase

import com.estebanposada.domain.repository.ArtistRepository

class GetArtistsUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke() = repository.getArtists()
}