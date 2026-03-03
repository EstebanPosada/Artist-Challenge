package com.estebanposada.artischallenge.repository

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(): ArtistRepository {
    override suspend fun getArtists(): Resource<List<Artist>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtistById(id: String): Resource<Artist> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbumsByArtistId(id: String): Resource<List<Album>> {
        TODO("Not yet implemented")
    }
}