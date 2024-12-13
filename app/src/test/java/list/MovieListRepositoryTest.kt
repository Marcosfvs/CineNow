package list

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.model.Movie
import com.devspacecinenow.list.data.LocalDataSource
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

class MovieListRepositoryTest {
    //local em fake
    //remote em mock
    private val local = FakeMovieListLocalDataSource ()
    private val remote: MovieListRemoteDataSource = mock()

    private val underTest by lazy {
        MovieListRepository(
            local = local,
            remote = remote
        )
    }

    @Test
    fun `Given no internet connection When getting now playing movies Then return local data`() {
        runTest {
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name

                )
            )
            whenever(remote.getNowPlaying()).thenReturn(Result.failure(UnknownHostException("no internet")))
            local.nowPlaying = localList

            val result = underTest.getNowPlaying()
            val expected = Result.success(localList)
            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given no internet connection and no local data When getting now playing movies Then return remote result`() {
        runTest {
            val remoteResult = Result.failure<List<Movie>>(UnknownHostException("no internet"))
            whenever(remote.getNowPlaying()).thenReturn(remoteResult)

            local.nowPlaying = emptyList()

            val result = underTest.getNowPlaying()
            val expected = remoteResult
            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given remote success When getting now playing movies Then return update local data`() {
        runTest {
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name

                )
            )
            val remoteResult = Result.success(list)
            whenever(remote.getNowPlaying()).thenReturn(remoteResult)
            local.nowPlaying = list

            val result = underTest.getNowPlaying()
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)

        }
    }
}
