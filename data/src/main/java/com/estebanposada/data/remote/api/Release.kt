package com.estebanposada.data.remote.api

data class Release(
    val artist: String,
    val format: String,
    val id: Int,
    val label: String,
    val main_release: Int,
    val resource_url: String,
    val role: String,
    val status: String,
    val thumb: String?,
    val title: String,
    val trackinfo: String,
    val type: String,
    val year: Int?
)