package com.estebanposada.artischallenge.ui.search

import com.estebanposada.domain.model.Artist

//sealed class SearchArtistState {
//    object Loading : SearchArtistState()
//    object Idle : SearchArtistState()
//    data class Success(
//        val query: String,
//        val artists: List<Artist>,
//        val isLoadingMore: Boolean,
//        val canLoadMore: Boolean
//    ) : SearchArtistState()
//
//    object Error : SearchArtistState()
//}
data class SearchArtistState(
    val query: String = "",
    val artists: List<Artist> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val error: String? = null
)

sealed class SearchArtistAction{
    data class OnQueryChange(val query: String): SearchArtistAction()
    object OnLoadNextPage: SearchArtistAction()
    data class OnArtistClick(val id: String): SearchArtistAction()
}
sealed class SearchArtistEvent {
    data class ItemClicked(val id: String) : SearchArtistEvent()
}