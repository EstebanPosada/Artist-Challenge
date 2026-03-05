package com.estebanposada.domain.usecase

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.repository.FakeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchAlbumUseCaseTest {
    private lateinit var searchAlbumUseCase: SearchAlbumUseCase
    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        repository = FakeRepository()
        searchAlbumUseCase = SearchAlbumUseCase(repository)
    }

    @Test
    fun `when searchAlbumUseCase is called and repo returns error`() = runTest {
        val id = "id"
        val page = 1
        val error = RuntimeException("network error")

        repository.albumList = Resource.Error(error)
        val result = searchAlbumUseCase(id, page)

        assertEquals(Resource.Error(error), result)
    }

    @Test
    fun `when searchAlbumUseCase is called, then repository returns successfully`() = runTest {
        val id = "id"
        val page = 1
        val album = Album(id = "12", title = "Test", year = 0)

        repository.albumList = Resource.Success(listOf(album))
        val result = searchAlbumUseCase(id, page)

        assertEquals(Resource.Success(listOf(album)), result)
    }
}