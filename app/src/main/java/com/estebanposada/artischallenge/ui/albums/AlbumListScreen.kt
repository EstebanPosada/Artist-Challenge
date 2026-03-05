package com.estebanposada.artischallenge.ui.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.estebanposada.artischallenge.ui.albums.components.AlbumItem
import com.estebanposada.artischallenge.ui.albums.components.AlbumSortType
import com.estebanposada.artischallenge.ui.albums.components.SortModalBottomSheet
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import com.estebanposada.domain.model.Album

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel = hiltViewModel(), onBack: () -> Unit) {
    val state = viewModel.state.value
    AlbumList(
        state,
        onBack = onBack,
        onSort = viewModel::doSort,
        onLoadMore = viewModel::onLoadMore
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumList(
    state: AlbumListState,
    onBack: () -> Unit,
    onSort: (AlbumSortType) -> Unit,
    onLoadMore: () -> Unit
) {
    var showSortSheet by remember { mutableStateOf(false) }
    if (showSortSheet)
        SortModalBottomSheet(
            selectedSort = state.selectedSort,
            onSort = { onSort(it).also { showSortSheet = false } },
            onDismiss = { showSortSheet = false }
        )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = { showSortSheet = true }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Filter"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            elevation = CardDefaults.cardElevation(8.dp),
        ) {
            if (state.isLoading)
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            state.error?.let {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Error",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Text(
                "Albums (Sorted by: ${state.selectedSort})",
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(state.filteredAlbums) { i, album ->
                    AlbumItem(modifier = Modifier, album)
                    if (i >= state.albums.size - 3 && state.canLoadMore && !state.isLoadingMore) {
                        onLoadMore()
                    }
                }
                if (state.isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AlbumListPreview() {
    val album = Album(id = "123", title = "Title", year = 123)
    val albums = listOf(album, album)
    val state = AlbumListState(albums = albums)
    ArtisChallengeTheme {
        AlbumList(state, {}, {}) {}
    }
}