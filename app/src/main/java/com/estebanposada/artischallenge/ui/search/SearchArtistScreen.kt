package com.estebanposada.artischallenge.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    SearchArtist(state) { onItemClick(it) }
}

@Composable
private fun SearchArtist(state: SearchArtistState, onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SearchBar(
            query = "",
            onQueryChange = { },
            onSearch = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            when (state) {
                is SearchArtistState.Loading -> CircularProgressIndicator()
                is SearchArtistState.Success ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.artists) { artist ->
                            ArtistItem(artist) { onItemClick(it) }
                        }
                    }

                is SearchArtistState.Error -> Text(
                    "Something went wrong",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchArtistPreview() {
    val artist = Artist(id = "id", name = "name", thumbnail = "url")
    val state = SearchArtistState.Success(listOf(artist, artist))
    ArtisChallengeTheme {
        SearchArtist(state) {}
    }
}

@Preview
@Composable
private fun SearchArtistPreviewEmpty() {
    val state = SearchArtistState.Success(listOf())
    ArtisChallengeTheme {
        SearchArtist(state) {}
    }
}

@Preview
@Composable
private fun SearchArtistPreviewLoading() {
    val state = SearchArtistState.Loading
    ArtisChallengeTheme {
        SearchArtist(state) {}
    }
}

@Preview
@Composable
private fun SearchArtistPreviewError() {
    val state = SearchArtistState.Error
    ArtisChallengeTheme {
        SearchArtist(state) {}
    }
}