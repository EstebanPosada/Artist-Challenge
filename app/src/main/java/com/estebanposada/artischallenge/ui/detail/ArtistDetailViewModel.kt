package com.estebanposada.artischallenge.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.GetArtistByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val getArtistByIdUseCase: GetArtistByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf<ArtistDetailState>(ArtistDetailState.Loading)
    val state: State<ArtistDetailState> = _state

    companion object {
        const val ARTIST_ID = "artistId"
    }

    init {
        savedStateHandle.get<String>(ARTIST_ID)?.let { id ->
            getArtistDetail(id)
        }
    }

    fun getArtistDetail(id: String) {
        viewModelScope.launch {
            _state.value = ArtistDetailState.Loading
            when (val result = getArtistByIdUseCase(id)) {
                is Resource.Success -> _state.value = ArtistDetailState.Success(result.data)
                is Resource.Error -> _state.value = ArtistDetailState.Error
            }
        }
    }


}