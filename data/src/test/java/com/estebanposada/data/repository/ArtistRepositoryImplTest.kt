package com.estebanposada.data.repository

import com.estebanposada.data.remote.api.ArtistApi
import com.estebanposada.data.remote.api.SearchArtistResponse
import com.estebanposada.data.remote.api.toAlbum
import com.estebanposada.data.remote.api.toArtist
import com.estebanposada.data.remote.api.toArtistDetail
import com.estebanposada.domain.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ArtistRepositoryImplTest {
    private lateinit var repository: ArtistRepositoryImpl
    private lateinit var api: ArtistApi

    @Before
    fun setUp() {
        api = mockk<ArtistApi>()
        repository = ArtistRepositoryImpl(api)
    }

    @Test
    fun `when searchArtists is called, then api throws error`() = runTest {
        val query = "q"
        val page = 1

        coEvery { api.searchArtists(query = query, page = page) } throws RuntimeException("error")
        when (val result = repository.searchArtists(query, page)) {
            is Resource.Error -> {
                assert(result.cause is RuntimeException)
                assertEquals("error", result.cause?.message)
            }

            else -> error("Error")
        }
    }

    @Test
    fun `when searchArtists is called, then api return successful data`() = runTest {
        val query = "q"
        val page = 1

        val searchResponse = SearchArtistResponse(listOf(artistDto))

        coEvery { api.searchArtists(query = query, page = page) } returns searchResponse

        when (val result = repository.searchArtists(query = query, page = page)) {
            is Resource.Success -> {
                assertEquals(listOf(artistDto).map { it.toArtist() }, result.data)
            }

            else -> error("Error")
        }
    }

    @Test
    fun `when getAlbumsByArtistId is called, then api throws error`() = runTest {
        val id = "id"
        val page = 1

        coEvery { api.getAlbums(id = id, page = page) } throws RuntimeException("error")
        when (val result = repository.getAlbumsByArtistId(id, page)) {
            is Resource.Error -> {
                assert(result.cause is RuntimeException)
                assertEquals("error", result.cause?.message)
            }

            else -> error("Error")
        }
    }

    @Test
    fun `when getAlbumsByArtistId is called, then api return successful data`() = runTest {
        val id = "id"
        val page = 1

        coEvery { api.getAlbums(id = id, page = page) } returns albumResponse

        when (val result = repository.getAlbumsByArtistId(id, page)) {
            is Resource.Success -> {
                assertEquals(listOf(albumResponse).map { it.toAlbum() }, result.data)
            }

            else -> error("Error")
        }
    }

    @Test
    fun `when getArtistDetailById is called, then api throws error`() = runTest {
        val id = "id"

        coEvery { api.getArtistById(id) } throws RuntimeException("error")
        when (val result = repository.getArtistDetailById(id)) {
            is Resource.Error -> {
                assert(result.cause is RuntimeException)
                assertEquals("error", result.cause?.message)
            }

            else -> error("Error")
        }
    }

    @Test
    fun `when getArtistDetailById is called, then api return successful data`() = runTest {
        val id = "id"

        coEvery { api.getArtistById(id) } returns artistResponse

        when (val result = repository.getArtistDetailById(id)) {
            is Resource.Success -> {
                assertEquals(artistResponse.toArtistDetail(), result.data)
            }

            else -> error("Error")
        }
    }

    @Test
    fun `when getAlbumInfo is called, then api throws error`() = runTest {
        val id = "id"

        coEvery { api.getAlbumInfo(id) } throws RuntimeException("error")
        when (val result = repository.getAlbumInfo(id)) {
            is Resource.Error -> {
                assert(result.cause is RuntimeException)
                assertEquals("error", result.cause?.message)
            }

            else -> error("Error")
        }
    }

    @Test
    fun `when getAlbumInfo is called, then api return successful data`() = runTest {
        val id = "id"

        coEvery { api.getAlbumInfo(id) } returns albumResponse

        when (val result = repository.getAlbumInfo(id)) {
            is Resource.Success -> {
                assertEquals(albumResponse.toAlbum(), result.data)
            }

            else -> error("Error")
        }
    }
}
