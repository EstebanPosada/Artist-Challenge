package com.estebanposada.artischallenge.ui.albums

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.artischallenge.ui.albums.components.AlbumSortType
import com.estebanposada.artischallenge.ui.common.onFailure
import com.estebanposada.artischallenge.ui.common.onSuccess
import com.estebanposada.artischallenge.ui.common.toUiError
import com.estebanposada.artischallenge.ui.detail.ArtistDetailViewModel.Companion.ARTIST_ID
import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.usecase.GetAlbumInfoUseCase
import com.estebanposada.domain.usecase.SearchAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchAlbumUseCase: SearchAlbumUseCase,
    private val getAlbumInfoUseCase: GetAlbumInfoUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AlbumListState())
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
                searchAlbumUseCase(id, currentPage).onSuccess { result ->
                    val albums = result.sortedByDescending { it.year ?: 0 }
                    _state.value = _state.value.copy(
                        isLoading = false,
                        albums = albums,
                        filteredAlbums = albums
                    )
                    getAlbumInfo(albums)
                }.onFailure { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.toUiError()
                    )
                }
            }
        }
    }

    private fun getAlbumInfo(albums: List<Album>) {
        viewModelScope.launch {
            async {
                val updatedAlbums = albums.map { album ->
                    when (val result = getAlbumInfoUseCase(album.id)) {
                        is Resource.Error -> album
                        is Resource.Success -> {
                            val info = result.data
                            album.copy(genres = info.genres, labels = info.labels)
                        }
                    }
                }
                _state.value =
                    _state.value.copy(albums = updatedAlbums, filteredAlbums = updatedAlbums)
            }
        }
    }


    fun onLoadMore() {
        val currentArtistId = artistId ?: return
        val currentState = _state.value
        if (currentState.isLoadingMore || !currentState.canLoadMore) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingMore = true, error = null)
            currentPage++
            searchAlbumUseCase(currentArtistId, currentPage).onSuccess { result ->
                val albums = _state.value.albums + result
                val sorted = albums.sortedByDescending { it.year ?: 0 }
                _state.value = _state.value.copy(
                    albums = sorted,
                    filteredAlbums = sorted,
                    isLoadingMore = false,
                    canLoadMore = result.isNotEmpty()
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    isLoadingMore = false,
                    isLoading = false,
                    error = error.toUiError()
                )
            }
        }
    }

    fun doSort(type: AlbumSortType) {
        val albums = _state.value.albums
        val sorted = when (type) {
            AlbumSortType.YEAR -> albums.sortedBy { it.year }
            AlbumSortType.GENRE -> albums.sortedBy { it.genres.firstOrNull() ?: "" }
            AlbumSortType.LABEL -> albums.sortedBy { it.labels.firstOrNull() ?: "" }
        }
        _state.value = _state.value.copy(filteredAlbums = sorted, selectedSort = type)
    }
}
