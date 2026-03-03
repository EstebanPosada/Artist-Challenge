package com.estebanposada.artischallenge.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf<ArtistDetailState>(ArtistDetailState.Loading)
    val state: State<ArtistDetailState> = _state


}