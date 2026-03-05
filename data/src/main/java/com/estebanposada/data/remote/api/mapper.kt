package com.estebanposada.data.remote.api

import com.estebanposada.domain.model.Album
import com.estebanposada.domain.model.Artist

fun ArtistDto.toArtist() = Artist(
    id = id.toString(),
    name = title,
    thumbnail = thumb?.takeIf { it.isNotBlank() } ?: cover_image,
    members = emptyList(),
)

fun ArtistResponse.toArtistDetail() = Artist(
    id = id.toString(),
    name = name,
    thumbnail = images?.firstOrNull()?.uri,
    profile = profile.orEmpty(),
    members = members?.map { it.name },
)
fun AlbumResponse.toAlbum() = Album(
    id = "",
    title = title,
    year = year,
    genres = genres,
    labels = labels.map { it.name }
)

fun ReleaseDto.toAlbum() = Album(
    id = id.toString(),
    title = title,
    year = year ?: 0,
//    genres = ,
//    labels = TODO(),
)