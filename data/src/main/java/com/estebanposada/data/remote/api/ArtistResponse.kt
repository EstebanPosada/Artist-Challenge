package com.estebanposada.data.remote.api

data class ArtistResponse(
    val id: Int,
    val name: String,
    val profile: String? = null,
    val images: List<ImageDto>? = null,
    val members: List<MemberDto>? = null
)

data class ImageDto(
    val uri: String,
    val type: String
)

data class MemberDto(
    val id: String,
    val name: String
)