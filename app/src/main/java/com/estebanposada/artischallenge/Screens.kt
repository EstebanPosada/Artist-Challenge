package com.estebanposada.artischallenge

import kotlinx.serialization.Serializable

@Serializable
object SearchScreen

@Serializable
data class DetailArtistScreen(val artistId: String)

@Serializable
data class AlbumScreen(val artistId: String)