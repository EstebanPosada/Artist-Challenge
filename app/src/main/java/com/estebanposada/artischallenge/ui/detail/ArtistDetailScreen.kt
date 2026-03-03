package com.estebanposada.artischallenge.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import com.estebanposada.domain.model.Artist

@Composable
fun ArtistDetailScreen(
    viewModel: ArtistDetailViewModel = hiltViewModel(),
    onClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.value
    ArtistDetail(state, onClick = onClick, onBack = onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistDetail(state: ArtistDetailState, onClick: (String) -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is ArtistDetailState.Loading ->
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                is ArtistDetailState.Success -> {
                    Card(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentScale = ContentScale.Fit,
                            contentDescription = "Artist: ${state.artist.id}",
                            modifier = Modifier
                                .aspectRatio(3 / 4f)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Text(state.artist.name, style = MaterialTheme.typography.titleLarge)
                        Text(state.artist.thumbnail, style = MaterialTheme.typography.bodyMedium)
                        Text(state.artist.thumbnail, style = MaterialTheme.typography.bodySmall)
                    }
                }

                is ArtistDetailState.Error ->
                    Text(
                        "Error",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
            }
        }
    }
}

@Preview
@Composable
private fun ArtistDetailPreview() {
    val artist = Artist(id = "id", name = "name", thumbnail = "url")
    val state = ArtistDetailState.Success(artist)
    ArtisChallengeTheme {
        ArtistDetail(state, {}, {})
    }
}

@Preview
@Composable
private fun ArtistDetailLoadingPreview() {
    val state = ArtistDetailState.Loading
    ArtisChallengeTheme {
        ArtistDetail(state, {}, {})
    }
}

@Preview
@Composable
private fun ArtistDetailErrorPreview() {
    val state = ArtistDetailState.Error
    ArtisChallengeTheme {
        ArtistDetail(state, {}, {})
    }
}