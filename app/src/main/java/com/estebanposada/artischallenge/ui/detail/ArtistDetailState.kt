package com.estebanposada.artischallenge.ui.detail

import com.estebanposada.domain.model.Artist

sealed class ArtistDetailState {
    object Loading : ArtistDetailState()
    data class Success(val artist: Artist) : ArtistDetailState()
    object Error : ArtistDetailState()
}