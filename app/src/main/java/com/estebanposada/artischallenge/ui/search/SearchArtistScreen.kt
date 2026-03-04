package com.estebanposada.artischallenge.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.estebanposada.artischallenge.ui.search.components.ArtistItem
import com.estebanposada.artischallenge.ui.search.components.SearchBar
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import com.estebanposada.domain.model.Artist

@Composable
fun SearchArtistScreen(
    viewModel: SearchArtistViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    val state = viewModel.state.value
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is SearchArtistEvent.ItemClicked -> onItemClick(event.id)
            }
        }
    }
    SearchArtist(
        state, onItemClick = { viewModel.onItemClick(it) },
        onQueryChange = { viewModel.onSearch(it) },
        onLoadNextPage = { viewModel.onLoadNextPage() })
}

@Composable
private fun SearchArtist(
    state: SearchArtistState,
    onItemClick: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onLoadNextPage: () -> Unit
) {
    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(8.dp),
                query = state.query,
                onQueryChange = { onQueryChange(it) },
                onSearch = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(state.artists) { i, artist ->
                    ArtistItem(artist) { onItemClick(it) }
                    if (i >= state.artists.size - 3 && state.canLoadMore && !state.isLoadingMore) {
                        onLoadNextPage()
                    }
                }
            }
            if (state.isLoading)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            state.error?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Something went wrong",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchArtistPreview() {
    val artist = Artist(id = "id", name = "name", thumbnail = "url", title = "title")
    val state = SearchArtistState(artists = listOf(artist, artist))
    ArtisChallengeTheme {
        SearchArtist(state, {}, {}) {}
    }
}

@Preview
@Composable
private fun SearchArtistPreviewEmpty() {
    val state = SearchArtistState(artists = emptyList())
    ArtisChallengeTheme {
        SearchArtist(state, {}, {}) {}
    }
}

@Preview
@Composable
private fun SearchArtistPreviewLoading() {
    val state = SearchArtistState(isLoading = true)
    ArtisChallengeTheme {
        SearchArtist(state, {}, {}) {}
    }
}

@Preview
@Composable
private fun SearchArtistPreviewError() {
    val state = SearchArtistState(error = "error")
    ArtisChallengeTheme {
        SearchArtist(state, {}, {}) {}
    }
}