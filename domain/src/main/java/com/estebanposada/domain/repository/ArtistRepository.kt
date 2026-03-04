package com.estebanposada.domain.repository

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.model.ArtistDetail

interface ArtistRepository {
    suspend fun getArtistDetailById(id: String): Resource<ArtistDetail>
    suspend fun searchArtists(query: String, page: Int): Resource<List<Artist>>
    suspend fun getAlbumsByArtistId(id: String): Resource<List<Album>>
}