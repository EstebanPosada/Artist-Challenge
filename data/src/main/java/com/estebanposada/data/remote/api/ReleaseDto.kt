package com.estebanposada.data.remote.api

data class ReleaseDto(
    val id: Int,
    val title: String,
    val type: String,
    val thumb: String?,
    val year: Int?,
    val artist: String,
)
