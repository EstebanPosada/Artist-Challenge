package com.estebanposada.artischallenge.ui.detail

import com.estebanposada.artischallenge.ui.common.UiError
import com.estebanposada.domain.model.Artist

data class ArtistDetailState(
    val id: String = "",
    val artist: Artist? = null,
    val isLoading: Boolean = false,
    val error: UiError? = null
)
