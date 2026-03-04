package com.estebanposada.data.remote.api

data class SearchArtistResponse(
    val paginationDto: PaginationDto,
    val results: List<ArtistDto>
)
