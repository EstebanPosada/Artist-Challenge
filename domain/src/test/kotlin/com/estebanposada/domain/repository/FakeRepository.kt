package com.estebanposada.domain.repository

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.model.Artist

class FakeRepository : ArtistRepository {
    var album: Resource<Album> = Resource.Error()
    var albumList: Resource<List<Album>> = Resource.Error()
    var artist: Resource<Artist> = Resource.Error()
    var artistList: Resource<List<Artist>> = Resource.Error()

    override suspend fun getArtistDetailById(id: String): Resource<Artist> = artist

    override suspend fun searchArtists(query: String, page: Int): Resource<List<Artist>> =
        artistList

    override suspend fun getAlbumsByArtistId(id: String, page: Int): Resource<List<Album>> =
        albumList

    override suspend fun getAlbumInfo(id: String): Resource<Album> = album

}