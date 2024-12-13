package list

import com.devspacecinenow.common.model.Movie
import com.devspacecinenow.list.data.LocalDataSource

class FakeMovieListLocalDataSource: LocalDataSource {
    var nowPlaying = emptyList<Movie>()
    var topRated = emptyList<Movie>()
    var upComing = emptyList<Movie>()
    var popular = emptyList<Movie>()
    var updateItems = emptyList<Movie>()

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return nowPlaying
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return topRated
    }

    override suspend fun getUpComingMovies(): List<Movie> {
        return upComing
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return popular
    }

    override suspend fun updateLocalItems(movies: List<Movie>) {
        updateItems = movies
    }
}