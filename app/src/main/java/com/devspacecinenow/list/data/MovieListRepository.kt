package com.devspacecinenow.list.data

import android.accounts.NetworkErrorException
import com.devspacecinenow.common.model.MovieResponse


class MovieListRepository(
    private val listService: ListService
) {

    suspend fun getNowPlaying(): Result<MovieResponse?> {
        //try tenta fazer tal coisa, se não conseguir catch(segura) o erro não mostra para o usuário.
        return  try {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRated(): Result<MovieResponse?> {
        return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }

    }

    suspend fun getUpComing(): Result<MovieResponse?> {
        return try {
            val response = listService.getUpcomingMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopular(): Result<MovieResponse?>{
        return try {
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
    }
}