package com.estebanposada.artischallenge.ui.search

import com.estebanposada.domain.model.Artist

data class SearchArtistState(
    val query: String = "",
    val artists: List<Artist> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val error: String? = null
)

sealed class SearchArtistEvent {
    data class ItemClicked(val id: String) : SearchArtistEvent()
}