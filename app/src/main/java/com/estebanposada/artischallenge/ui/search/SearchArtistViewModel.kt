package com.estebanposada.artischallenge.ui.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.artischallenge.ui.common.onFailure
import com.estebanposada.artischallenge.ui.common.onSuccess
import com.estebanposada.artischallenge.ui.common.toUiError
import com.estebanposada.domain.usecase.SearchArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(private val searchArtistsUseCase: SearchArtistsUseCase) :
    ViewModel() {
    private val _state = mutableStateOf(SearchArtistState())
    val state: State<SearchArtistState> = _state

    private var currentPage = 1
    private var searchJob: Job? = null

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

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchArtistsUseCase(query = query, page = 1).onSuccess { artists ->
                _state.value = _state.value.copy(
                    artists = artists,
                    isLoading = false,
                    canLoadMore = artists.isNotEmpty(),
                    error = null
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = error.toUiError()
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
            searchArtistsUseCase(
                query = currentState.query,
                page = currentPage
            ).onSuccess { result ->
                val artists = currentState.artists + result
                _state.value = currentState.copy(
                    artists = artists,
                    isLoading = false,
                    isLoadingMore = false,
                    canLoadMore = result.isNotEmpty(),
                    error = null
                )
            }.onFailure { error ->
                _state.value = currentState.copy(
                    isLoadingMore = false,
                    isLoading = false,
                    error = error.toUiError()
                )
                currentPage--
            }
        }
    }
}

