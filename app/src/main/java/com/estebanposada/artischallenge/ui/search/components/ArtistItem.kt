package com.estebanposada.artischallenge.ui.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import com.estebanposada.domain.model.Artist

@Composable
fun ArtistItem(artist: Artist, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        onClick = { onClick(artist.id) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.thumbnail).crossfade(true)
                    .placeholder(R.drawable.artist_placeholder)
                    .error(R.drawable.artist_error).build()
            )
            Image(
                painter = painter,
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(R.string.artist_value, artist.id),
                modifier = Modifier
                    .width(60.dp)
                    .aspectRatio(3 / 4f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                artist.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.arrow),
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtistItemPreview() {
    val artist = Artist(
        id = "id", name = "name", thumbnail = "url",
        members = emptyList()
    )
    ArtisChallengeTheme {
        ArtistItem(artist) {}
    }
}
