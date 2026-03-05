package com.estebanposada.domain.usecase

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.repository.ArtistRepository

class SearchArtistsUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(query: String, page: Int): Resource<List<Artist>> =
        repository.searchArtists(query = query, page = page)
}