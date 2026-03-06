package com.estebanposada.data.repository

import com.estebanposada.data.remote.api.ArtistApi
import com.estebanposada.data.remote.api.toAlbum
import com.estebanposada.data.remote.api.toArtist
import com.estebanposada.data.remote.api.toArtistDetail
import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(private val api: ArtistApi) :
    ArtistRepository {
    override suspend fun getArtistDetailById(id: String): Resource<Artist> =
        safeApiCall { api.getArtistById(id).toArtistDetail() }

    override suspend fun searchArtists(
        query: String,
        page: Int
    ): Resource<List<Artist>> = safeApiCall {
        api.searchArtists(query = query, page = page).results.filter { it.type == "artist" }
            .map { it.toArtist() }
    }

    override suspend fun getAlbumsByArtistId(
        id: String,
        page: Int
    ): Resource<List<Album>> = safeApiCall {
        api.getAlbums(id, page).releases.filter { it.type == "master" }.map { it.toAlbum() }
    }

    override suspend fun getAlbumInfo(id: String): Resource<Album> = safeApiCall {
        api.getAlbumInfo(id).toAlbum()
    }
}
