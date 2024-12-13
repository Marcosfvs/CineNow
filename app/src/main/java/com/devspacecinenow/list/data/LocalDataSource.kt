package com.devspacecinenow.list.data

import com.devspacecinenow.common.model.Movie

interface LocalDataSource {
    // Interface é um contrato
    //test unitários com o fake
    //obrigatorio implementar funções

    suspend fun getNowPlayingMovies(): List<Movie>

    suspend fun getTopRatedMovies(): List<Movie>

    suspend fun getUpComingMovies(): List<Movie>

    suspend fun getPopularMovies(): List<Movie>

    suspend fun updateLocalItems(movies: List<Movie>)
}