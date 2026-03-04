package com.estebanposada.domain.usecase

import com.estebanposada.domain.repository.ArtistRepository

class SearchAlbumUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(id: String, page: Int) = repository.getAlbumsByArtistId(id, page)
}