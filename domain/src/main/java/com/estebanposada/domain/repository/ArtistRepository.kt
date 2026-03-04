package com.estebanposada.domain.repository

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.model.Artist

interface ArtistRepository {
    suspend fun getArtistDetailById(id: String): Resource<Artist>
    suspend fun searchArtists(query: String, page: Int): Resource<List<Artist>>
    suspend fun getAlbumsByArtistId(id: String, page: Int): Resource<List<Album>>
    suspend fun getAlbumInfo(id: String): Resource<List<Album>>
}