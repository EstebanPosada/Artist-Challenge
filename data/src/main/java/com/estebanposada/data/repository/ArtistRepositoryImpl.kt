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
    override suspend fun getArtistDetailById(id: String): Resource<Artist> = try {
        val response = api.getArtistById(id)
        Resource.Success(response.toArtistDetail())
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun searchArtists(
        query: String,
        page: Int
    ): Resource<List<Artist>> = try {
        val response = api.searchArtists(query = query, page = page)
            .results.filter { it.type == "artist" }.map { it.toArtist() }
        Resource.Success(response)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getAlbumsByArtistId(
        id: String,
        page: Int
    ): Resource<List<Album>> = try {
        val response = api.getAlbums(id, page)
        Resource.Success(response.releases.filter { it.type == "master" }.map { it.toAlbum() })
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getAlbumInfo(id: String): Resource<List<Album>> = try {
        val response = api.getAlbumInfo(id)
        Resource.Success(response.releases.map { it.toAlbum() })
    } catch (e: Exception) {
        Resource.Error(e)
    }
}