package com.estebanposada.domain.usecase

import com.estebanposada.domain.repository.ArtistRepository

class SearchArtistsUseCase (private val repository: ArtistRepository) {
    suspend operator fun invoke(query: String, page: Int) =
        repository.searchArtists(query = query, page = page)
}