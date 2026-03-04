package com.estebanposada.data.remote.api

import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.model.ArtistDetail

fun ArtistDto.toArtist() = Artist(
    id = id.toString(),
    name = title,
    thumbnail = thumb?.takeIf { it.isNotBlank() }?:cover_image,
    title = title
)

fun ArtistResponse.toArtistDetail() = ArtistDetail(
    id = id.toString(),
    name = name,
    profile = profile.orEmpty(),
    imageUrl = images?.firstOrNull()?.uri,
    members = members?.map {it.name },
)