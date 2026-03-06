package com.estebanposada.data.repository

import com.estebanposada.data.remote.api.ArtistApi
import com.estebanposada.data.remote.api.SearchArtistResponse
import com.estebanposada.data.remote.api.toAlbum
import com.estebanposada.data.remote.api.toArtist
import com.estebanposada.data.remote.api.toArtistDetail
import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class ArtistRepositoryImplTest {
    private lateinit var repository: ArtistRepositoryImpl
    private lateinit var api: ArtistApi

    @Before
    fun setUp() {
        api = mockk<ArtistApi>()
        repository = ArtistRepositoryImpl(api)
    }

    @Test
    fun `when searchArtists is called, then api throws unknown error`() = runTest {
        val query = "q"
        val page = 1

        coEvery { api.searchArtists(query = query, page = page) } throws RuntimeException()
        val result = repository.searchArtists(query, page)
        assert(result is Resource.Error)
        assertEquals(Error.Unknown, (result as Resource.Error).error)
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
    fun `when getAlbumsByArtistId is called, then api throws 404 error`() = runTest {
        val id = "id"
        val page = 1

        coEvery { api.getAlbums(id = id, page = page) } throws httpException(404)
        val result = repository.getAlbumsByArtistId(id, page)

        assert(result is Resource.Error)
        assertEquals(Error.NotFound, (result as Resource.Error).error)
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
    fun `when getArtistDetailById is called, then api throws 401 error`() = runTest {
        val id = "id"

        coEvery { api.getArtistById(id) } throws httpException(401)
        val result = repository.getArtistDetailById(id)

        assert(result is Resource.Error)
        assertEquals(Error.Unauthorized, (result as Resource.Error).error)
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
    fun `when getAlbumInfo is called, then api throws 429 error`() = runTest {
        val id = "id"

        coEvery { api.getAlbumInfo(id) } throws httpException(429)
        val result = repository.getAlbumInfo(id)

        assert(result is Resource.Error)
        assertEquals(Error.RequestLimit, (result as Resource.Error).error)
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

    fun httpException(code: Int): HttpException {
        val response = retrofit2.Response.error<Any>(code, "".toResponseBody())
        return HttpException(response)
    }
}
