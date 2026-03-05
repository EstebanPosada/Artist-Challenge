package com.estebanposada.artischallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.estebanposada.artischallenge.ui.albums.AlbumListScreen
import com.estebanposada.artischallenge.ui.detail.ArtistDetailScreen
import com.estebanposada.artischallenge.ui.search.SearchArtistScreen
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ArtisChallengeTheme {
                NavHost(
                    navController = navController,
                    startDestination = SearchScreen
                ) {
                    composable<SearchScreen> {
                        SearchArtistScreen { navController.navigate(DetailArtistScreen(it)) }
                    }
                    composable<DetailArtistScreen> {
                        val arg = it.toRoute<AlbumScreen>()
                        ArtistDetailScreen(
                            onViewAlbums = { navController.navigate(AlbumScreen(arg.artistId)) },
                            onBack = { navController.popBackStack() })
                    }
                    composable<AlbumScreen> {
                        AlbumListScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
