package com.estebanposada.domain.repository

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.model.Artist

interface ArtistRepository {
    suspend fun getArtists(): Resource<List<Artist>>
    suspend fun getArtistById(id: String): Resource<Artist>
    suspend fun getAlbumsByArtistId(id: String): Resource<List<Album>>
}