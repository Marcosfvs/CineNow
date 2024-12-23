package list

import app.cash.turbine.test
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieListViewModelTest {
    //criação do repository duble
    private val repository: MovieListRepository = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    //criação do viewmodel duble
    private val underTest by lazy {
        MovieListViewModel(repository, testDispatcher)
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated Then assert expected value`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.TopRated.name

                )
            )

            whenever(repository.getTopRated()).thenReturn(Result.success(movies))

            //When
            underTest.uiTopRatedMovies.test {
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1"
                        )
                    )
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to nowPlaying Then assert expected value`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name

                )
            )

            whenever(repository.getNowPlaying()).thenReturn(Result.success(movies))

            //When
            underTest.uiNowPlaying.test {
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1"
                        )
                    )
                )
                assertEquals(expected, awaitItem())

            }
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to UpComing Then assert expected value`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.UpComing.name

                )
            )

            whenever(repository.getUpComing()).thenReturn(Result.success(movies))

            //When
            underTest.uiUpComingMovies.test {
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1"
                        )
                    )
                )
                assertEquals(expected, awaitItem())

            }
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to Popular Then assert expected value`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Popular.name

                )
            )

            whenever(repository.getPopular()).thenReturn(Result.success(movies))

            //When
            underTest.uiPopularMovies.test {
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1"
                        )
                    )
                )
                assertEquals(expected, awaitItem())

            }
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to upComing Then assert loading state`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.UpComing.name

                )
            )

            whenever(repository.getUpComing()).thenReturn(Result.success(movies))

            //When
            val result = underTest.uiUpComingMovies.value

            //Then assert expected value
            val expected = MovieListUiState(
                isLoading = true
            )

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to nowPlaying Then assert loading state`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name

                )
            )

            whenever(repository.getNowPlaying()).thenReturn(Result.success(movies))

            //When
            val result = underTest.uiNowPlaying.value

            //Then assert expected value
            val expected = MovieListUiState(
                isLoading = true
            )

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated Then assert loading state`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.TopRated.name

                )
            )

            whenever(repository.getTopRated()).thenReturn(Result.success(movies))

            //When
            val result = underTest.uiTopRatedMovies.value

            //Then assert expected value
            val expected = MovieListUiState(
                isLoading = true
            )

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to Popular Then assert loading state`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Popular.name

                )
            )

            whenever(repository.getPopular()).thenReturn(Result.success(movies))

            //When
            val result = underTest.uiPopularMovies.value

            //Then assert expected value
            val expected = MovieListUiState(
                isLoading = true
            )

            assertEquals(expected, result)
        }
    }
}