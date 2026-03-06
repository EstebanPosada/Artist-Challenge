package com.estebanposada.artischallenge.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme

@Composable
fun ErrorState(modifier: Modifier = Modifier, error: UiError) {
    val message = when (error) {
        UiError.Network -> stringResource(R.string.network_error)
        UiError.NotFound -> stringResource(R.string.not_found_error)
        UiError.Unknown -> stringResource(R.string.unknown_error)
        UiError.RequestLimit -> stringResource(R.string.too_many_request_error)
        UiError.Unauthorized -> stringResource(R.string.unautorized_error)
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            message,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun ErrorStatePreview() {
    val error = UiError.Unknown
    ArtisChallengeTheme {
        ErrorState(error = error)
    }
}
