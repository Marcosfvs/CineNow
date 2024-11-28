package com.devspacecinenow.list.data

import com.devspacecinenow.common.data.remote.MovieResponse
import com.devspacecinenow.common.model.Movie
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource


class MovieListRepository(
    private val local: MovieListLocalDataSource,
    private val remote: MovieListRemoteDataSource
) {

    suspend fun getNowPlaying(): Result<List<Movie>?> {
        return try {
            val result = remote.getNowPlaying()
            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()
                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalItems(moviesRemote)
                }

            } else {
                val localData = local.getNowPlayingMovies()
                if (localData.isEmpty()) {
                    return result
                }
            }
            // Source of truth
            Result.success(local.getNowPlayingMovies())

        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }


    suspend fun getTopRated(): Result<MovieResponse?> {
        return Result.success(MovieResponse(emptyList()))
        /*return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }*/

    }

    suspend fun getUpComing(): Result<MovieResponse?> {
        return Result.success(MovieResponse(emptyList()))
        /*return try {
            val response = listService.getUpcomingMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }*/
    }

    suspend fun getPopular(): Result<MovieResponse?> {
        return Result.success(MovieResponse(emptyList()))
        /* return try {
             val response = listService.getPopularMovies()
             if (response.isSuccessful){
                 Result.success(response.body())
             }else{
                 Result.failure(NetworkErrorException(response.message()))
             }
         } catch (ex: Exception){
             ex.printStackTrace()
             Result.failure(ex)
         }
     }*/
    }
}
