package com.estebanposada.artischallenge.ui.albums

sealed class AlbumListState {
    object Loading : AlbumListState()
    data class Success(val artist: String) : AlbumListState()
    object Error : AlbumListState()
}