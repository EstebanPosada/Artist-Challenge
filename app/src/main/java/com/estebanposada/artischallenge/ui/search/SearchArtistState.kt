package com.estebanposada.artischallenge.ui.search

import com.estebanposada.domain.model.Artist

sealed class SearchArtistState(){
    object Loading: SearchArtistState()
    data class Success(val artists: List<Artist>): SearchArtistState()
    object Error: SearchArtistState()
}
