package com.estebanposada.domain.usecase

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.repository.ArtistRepository

class SearchAlbumUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(id: String, page: Int): Resource<List<Album>> =
        repository.getAlbumsByArtistId(id, page)
}