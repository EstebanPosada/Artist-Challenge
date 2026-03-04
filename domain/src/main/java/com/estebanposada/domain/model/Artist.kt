package com.estebanposada.domain.model

data class Artist(
    val id: String,
    val name: String,
    val title: String,
    val thumbnail: String? = null,
    val year: String? = null
)