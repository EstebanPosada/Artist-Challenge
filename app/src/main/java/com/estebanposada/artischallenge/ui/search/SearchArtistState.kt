package com.estebanposada.artischallenge.ui.search

import com.estebanposada.domain.model.Artist
import com.estebanposada.artischallenge.ui.common.UiError

data class SearchArtistState(
    val query: String = "",
    val artists: List<Artist> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val error: UiError? = null
)
