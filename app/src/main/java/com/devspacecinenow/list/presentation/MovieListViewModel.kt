package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow<List<MovieDTO>>(emptyList())
    private val _uiTopRatedMovies = MutableStateFlow<List<MovieDTO>>(emptyList())
    private val _uiUpComingMovies = MutableStateFlow<List<MovieDTO>>(emptyList())
    private val _uiPopularMovies = MutableStateFlow<List<MovieDTO>>(emptyList())

    val uiNowPlaying: StateFlow<List<MovieDTO>> = _uiNowPlaying
    val uiTopRatedMovies: StateFlow<List<MovieDTO>> = _uiTopRatedMovies
    val uiUpComingMovies: StateFlow<List<MovieDTO>> = _uiUpComingMovies
    val uiPopularMovies: StateFlow<List<MovieDTO>> = _uiPopularMovies

    init {
        fetchNowPlaying()
        fetchTopRatedMovies()
        fetchUpComing()
        fetchPopularMovies()
    }

    private fun fetchNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiNowPlaying.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }

        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiTopRatedMovies.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchUpComing() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getUpcomingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiUpComingMovies.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }

    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiPopularMovies.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return MovieListViewModel(
                    listService
                ) as T
            }
        }

    }
}
