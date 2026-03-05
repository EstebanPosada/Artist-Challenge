package com.estebanposada.artischallenge.ui.albums

import com.estebanposada.artischallenge.ui.albums.components.AlbumSortType
import com.estebanposada.domain.model.Album

data class AlbumListState(
    val id: String = "",
    val albums: List<Album> = emptyList(),
    val filteredAlbums: List<Album> = emptyList(),
    val selectedSort: AlbumSortType = AlbumSortType.YEAR,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val error: String? = null
)