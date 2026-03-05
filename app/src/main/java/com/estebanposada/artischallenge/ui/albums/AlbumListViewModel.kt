package com.estebanposada.artischallenge.ui.albums

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.artischallenge.ui.albums.components.AlbumSortType
import com.estebanposada.artischallenge.ui.detail.ArtistDetailViewModel.Companion.ARTIST_ID
import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.usecase.GetAlbumInfoUseCase
import com.estebanposada.domain.usecase.SearchAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
                when (val result = searchAlbumUseCase(id, currentPage)) {
                    is Resource.Success -> {
                        val albums = result.data.sortedByDescending { it.year ?: 0 }
                        _state.value = _state.value.copy(
                            isLoading = false,
                            albums = albums,
                            filteredAlbums = albums
                        )
                        getAlbumInfo(albums)
                    }

                    is Resource.Error -> _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.cause?.message ?: "Error"
                    )
                }
            }
        }
    }

    private fun getAlbumInfo(albums: List<Album>) {
        viewModelScope.launch {
            val updatedAlbums = albums.map { album ->
                when (val result = getAlbumInfoUseCase(album.id)) {
                    is Resource.Error -> album
                    is Resource.Success -> {
                        val info = result.data
                        album.copy(genres = info.genres, labels = info.labels)
                    }
                }
            }
            _state.value = _state.value.copy(albums = updatedAlbums, filteredAlbums = updatedAlbums)
        }
    }


    fun onLoadMore() {
        val currentArtistId = artistId ?: return
        val currentState = _state.value
        if (currentState.isLoadingMore || !currentState.canLoadMore) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingMore = true, error = null)
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