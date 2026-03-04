package com.estebanposada.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistApi {
    @GET("/database/search")
    suspend fun searchArtists(
        @Query("q") query: String,
        @Query("type") type: String = "artist",
        @Query("page") page: Int,
        @Query("per_page") limit: Int = 30,
    ): SearchArtistResponse

    @GET("/artists/{artist_id}")
    suspend fun getArtistById(
        @Path("artist_id") id: String
    ): ArtistResponse
}