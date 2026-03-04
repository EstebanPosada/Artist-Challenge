package com.estebanposada.artischallenge.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.detail.components.Chip
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import com.estebanposada.domain.model.ArtistDetail

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
        when (state) {
            is ArtistDetailState.Loading ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

            is ArtistDetailState.Error ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Text(
                        "Error",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            is ArtistDetailState.Success -> {
                val scrollState = rememberScrollState()
                Column(
                    Modifier
                        .verticalScroll(scrollState)
                        .padding(innerPadding)
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.artist.imageUrl).crossfade(true)
                                .placeholder(R.drawable.artist_placeholder)
                                .error(R.drawable.artist_error).build()
                        )
                        Image(
                            painter = painter,
                            contentScale = ContentScale.Fit,
                            contentDescription = "Artist: ${state.artist.id}",
                            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            state.artist.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(state.artist.profile, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    state.artist.members?.let { members ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp),
                        ) {
                            Text(
                                "Members",
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            FlowRow(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                members.forEach { artist ->
                                    Chip(name = artist)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { onClick(state.artist.id) },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("View albums")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ArtistDetailPreview() {
    val artist = ArtistDetail(
        id = "id", name = "name",
        profile = "profile",
        imageUrl = "image",
        members = listOf("Member 1", "Member 2"),
    )
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