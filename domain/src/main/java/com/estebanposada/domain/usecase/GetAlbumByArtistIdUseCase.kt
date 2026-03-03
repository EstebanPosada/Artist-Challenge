package com.estebanposada.domain.usecase

import com.estebanposada.domain.repository.ArtistRepository

class GetAlbumByArtistIdUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(id: String) = repository.getAlbumsByArtistId(id)
}