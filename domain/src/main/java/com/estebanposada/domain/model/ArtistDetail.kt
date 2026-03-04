package com.estebanposada.domain.model

data class ArtistDetail(
    val id: String,
    val name: String,
    val profile: String,
    val imageUrl: String? = null,
    val members: List<String>? = null
)
