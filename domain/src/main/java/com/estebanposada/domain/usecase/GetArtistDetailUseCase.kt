package com.estebanposada.domain.usecase

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.repository.ArtistRepository

class GetArtistDetailUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(id: String): Resource<Artist> = repository.getArtistDetailById(id)
}