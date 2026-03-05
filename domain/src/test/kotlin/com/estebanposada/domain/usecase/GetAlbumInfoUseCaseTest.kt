package com.estebanposada.domain.usecase

import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.repository.FakeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAlbumInfoUseCaseTest {
    private lateinit var getAlbumInfoUseCase: GetAlbumInfoUseCase
    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        repository = FakeRepository()
        getAlbumInfoUseCase = GetAlbumInfoUseCase(repository)
    }

    @Test
    fun `when getAlbumInfoUseCase is called and repo returns error`() = runTest {
        val id = "id"
        val error = RuntimeException("network error")

        repository.album = Resource.Error(error)
        val result = getAlbumInfoUseCase(id)

        assertEquals(Resource.Error(error), result)
    }

    @Test
    fun `when getAlbumInfoUseCase is called, then repository returns successfully`() = runTest {
        val id = "id"
        val album = Album(id = "12", title = "Test", year = 0)

        repository.album = Resource.Success(album)
        val result = getAlbumInfoUseCase(id)

        assertEquals(Resource.Success(album), result)
    }
}