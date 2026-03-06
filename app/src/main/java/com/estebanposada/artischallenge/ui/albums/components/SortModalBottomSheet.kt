package com.estebanposada.artischallenge.ui.albums.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortModalBottomSheet(
    modifier: Modifier = Modifier,
    onSort: (AlbumSortType) -> Unit,
    selectedSort: AlbumSortType,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(stringResource(R.string.filter), style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    selected = selectedSort == AlbumSortType.YEAR,
                    onClick = { onSort(AlbumSortType.YEAR) },
                    label = { Text(stringResource(R.string.filter_year)) })
                FilterChip(
                    selected = selectedSort == AlbumSortType.GENRE,
                    onClick = { onSort(AlbumSortType.GENRE) },
                    label = { Text(stringResource(R.string.filter_genre)) })
                FilterChip(
                    selected = selectedSort == AlbumSortType.LABEL,
                    onClick = { onSort(AlbumSortType.LABEL) },
                    label = { Text(stringResource(R.string.filter_label)) })
            }
        }
    }
}

@Preview
@Composable
private fun SortModalBottomSheetPreview() {
    ArtisChallengeTheme {
        SortModalBottomSheet(onSort = {}, selectedSort = AlbumSortType.GENRE) {}
    }
}
