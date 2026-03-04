package com.estebanposada.artischallenge.ui.detail

import com.estebanposada.domain.model.ArtistDetail

sealed class ArtistDetailState {
    object Loading : ArtistDetailState()
    data class Success(val artist: ArtistDetail) : ArtistDetailState()
    object Error : ArtistDetailState()
}