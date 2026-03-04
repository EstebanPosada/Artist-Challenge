package com.estebanposada.artischallenge.ui.albums

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.artischallenge.ui.detail.ArtistDetailViewModel.Companion.ARTIST_ID
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.SearchAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchAlbumUseCase: SearchAlbumUseCase,
) : ViewModel() {
    private val _state = mutableStateOf<AlbumListState>(AlbumListState())
    val state: State<AlbumListState> = _state
    private var currentPage = 1
    private var artistId: String? = null

    init {
        savedStateHandle.get<String>(ARTIST_ID)?.let { id ->
            artistId = id
            getAlbumsById()
        }
    }

    private fun getAlbumsById() {
        artistId?.let { id ->
            currentPage = 1
            viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true, error = null)
                when (val result = searchAlbumUseCase(id, currentPage)) {
                    is Resource.Success -> _state.value =
                        _state.value.copy(isLoading = false, albums = result.data)

                    is Resource.Error -> _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.cause?.message ?: "Error"
                    )
                }
            }
        }
    }


    fun onLoadMore() {
        val currentArtistId = artistId ?: return
        val currentState = _state.value
        if (currentState.isLoadingMore || !currentState.canLoadMore) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            currentPage++
            when (val result = searchAlbumUseCase(currentArtistId, currentPage)) {
                is Resource.Success -> {
                    val albums = currentState.albums + result.data
                    _state.value =
                        _state.value.copy(
                            albums = albums.sortedByDescending { it.year ?: 0 },
                            isLoadingMore = false,
                            canLoadMore = result.data.isNotEmpty()
                        )
                }

                is Resource.Error -> _state.value =
                    _state.value.copy(isLoadingMore = false, isLoading = false)
            }
        }
    }

}