package com.estebanposada.data.remote.api

data class AlbumResponse(
    val id: String,
    val year: Int? = null,
    val artist: String,
    val label: String,
    val labels: List<Label>,
    val title: String,
//    val released: String,
//    val released_formatted: String,
    val genres: List<String>,
//    val images: List<ImageDto>,
//    val thumb: String,
    val releases: List<ReleaseDto>
)

data class Label(
    val name: String,
)


