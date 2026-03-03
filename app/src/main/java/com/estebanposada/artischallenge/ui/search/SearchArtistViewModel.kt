package com.estebanposada.artischallenge.ui.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf<SearchArtistState>(SearchArtistState.Loading)
    val state: State<SearchArtistState> = _state

    fun onQueryChange(){

    }

    fun onSearch(query: String){
        if (query.isEmpty()) return

        viewModelScope.launch {

        }
    }
}