package com.estebanposada.data.repository

import com.estebanposada.data.remote.api.AlbumResponse
import com.estebanposada.data.remote.api.ArtistDto
import com.estebanposada.data.remote.api.ArtistResponse
import com.estebanposada.data.remote.api.Label
import com.estebanposada.data.remote.api.ReleaseDto

val artistDto = ArtistDto(
    cover_image = "123",
    id = 123,
    master_id = "123",
    master_url = "123",
    resource_url = "123",
    thumb = "thumb",
    title = "title",
    type = "artist",
    uri = "uri"
)

val artistResponse = ArtistResponse(
    id = 123,
    name = "name",
    profile = "profile",
    images = listOf(),
    members = listOf()
)

val label = Label("label")
val release = ReleaseDto(
    id = 123,
    title = "title",
    type = "master",
    thumb = "thumb",
    year = 1,
    artist = "artist"
)

val albumResponse = AlbumResponse(
    id = "123",
    year = 1,
    artist = "artist",
    label = "label",
    labels = listOf(),
    title = "title",
    genres = listOf(),
    releases = listOf(release)
)
