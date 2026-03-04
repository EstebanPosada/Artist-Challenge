package com.estebanposada.domain.model

data class Album(
    val id: String,
    val title: String,
    val year: Int?,
    val genres: List<String> = emptyList(),
    val labels: List<String> = emptyList()
)
