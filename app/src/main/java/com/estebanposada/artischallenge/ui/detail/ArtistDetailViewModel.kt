package com.estebanposada.artischallenge.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.GetArtistDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val getArtistDetailUseCase: GetArtistDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(ArtistDetailState())
    val state: State<ArtistDetailState> = _state

    companion object {
        const val ARTIST_ID = "artistId"
    }

    init {
        savedStateHandle.get<String>(ARTIST_ID)?.let { id ->
            loadArtistDetail(id)
        }
    }

    fun loadArtistDetail(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = getArtistDetailUseCase(id = id)) {
                is Resource.Success -> _state.value =
                    _state.value.copy(artist = result.data)

                is Resource.Error -> _state.value =
                    _state.value.copy(isLoading = false, error = "Unknown error")
            }
        }
    }
}