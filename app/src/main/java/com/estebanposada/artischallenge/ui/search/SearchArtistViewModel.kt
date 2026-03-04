package com.estebanposada.artischallenge.ui.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.SearchArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(private val searchArtistsUseCase: SearchArtistsUseCase) :
    ViewModel() {
    private val _state = mutableStateOf<SearchArtistState>(SearchArtistState())
    val state: State<SearchArtistState> = _state
    private val _event = MutableSharedFlow<SearchArtistEvent>()
    val event = _event.asSharedFlow()

    private var currentPage = 1

    fun onSearch(query: String) {
        if (query == _state.value.query) return
        currentPage = 1

        _state.value = _state.value.copy(
            query = query,
            artists = emptyList(),
            isLoading = true,
            isLoadingMore = false,
            error = null
        )

        viewModelScope.launch {
            when (val result = searchArtistsUseCase(query = query, page = 1)) {
                is Resource.Success -> _state.value = _state.value.copy(
                    artists = result.data,
                    isLoading = false,
                    canLoadMore = result.data.isNotEmpty(),
                    error = null
                )

                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    error = result.cause?.message ?: "Unknown error"
                )
            }
        }
    }

    fun onLoadNextPage() {
        val currentState = _state.value
        if (currentState.isLoadingMore || !currentState.canLoadMore) return

        _state.value = currentState.copy(isLoadingMore = true)
        currentPage++

        viewModelScope.launch {
            when (val result =
                searchArtistsUseCase(query = currentState.query, page = currentPage)) {
                is Resource.Success -> {
                    val artists = currentState.artists + result.data
                    _state.value = currentState.copy(
                        artists = artists,
                        isLoading = false,
                        canLoadMore = result.data.isNotEmpty(),
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = currentState.copy(
                        isLoading = false,
                        error = result.cause?.message ?: "Unknown error"
                    )
                    currentPage--
                }
            }
        }
    }

    fun onItemClick(artistId: String) {
        viewModelScope.launch {
            _event.emit(SearchArtistEvent.ItemClicked(artistId))
        }
    }
}