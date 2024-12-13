package com.devspacecinenow.list.data.local

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.local.MovieDao
import com.devspacecinenow.common.data.local.MovieEntity
import com.devspacecinenow.common.model.Movie
import com.devspacecinenow.list.data.LocalDataSource

class MovieListLocalDataSource (
    private val dao: MovieDao
): LocalDataSource {
    override suspend fun getNowPlayingMovies(): List<Movie>{
       return getMoviesByCategory(MovieCategory.NowPlaying)


    }

    override suspend fun getTopRatedMovies(): List<Movie>{
        return getMoviesByCategory(MovieCategory.TopRated)


    }
    override suspend fun getUpComingMovies(): List<Movie>{
        return getMoviesByCategory(MovieCategory.UpComing)


    }
    override suspend fun getPopularMovies(): List<Movie>{
        return getMoviesByCategory(MovieCategory.Popular)


    }

    override suspend fun updateLocalItems(movies: List<Movie>){
        val entities = movies.map {
            MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category
            )
        }
        dao.insertAll(entities)

    }

    private suspend fun getMoviesByCategory(movieCategory: MovieCategory): List<Movie> {
        val entities = dao.getMoviesByCategory(movieCategory.name)

        return entities.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category
            )
        }
    }
}