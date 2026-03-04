package com.estebanposada.domain.model

data class Artist(
    val id: String,
    val name: String,
    val profile: String? = null,
    val thumbnail: String? = null,
    val members: List<String>? = null
)