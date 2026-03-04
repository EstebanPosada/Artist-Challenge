package com.estebanposada.artischallenge.ui.detail

import com.estebanposada.domain.model.Artist

data class ArtistDetailState(
    val id: String = "",
    val artist: Artist? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)