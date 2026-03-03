package com.estebanposada.artischallenge.ui.albums

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf<AlbumListState>(AlbumListState.Loading)
    val state: State<AlbumListState> = _state

}