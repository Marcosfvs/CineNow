package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MovieListViewModel(
    listService: ListService
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
        listService.getNowPlayingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiNowPlaying.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }

        })
    }

    init {
        listService.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiTopRatedMovies.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }

        })
    }

    init {
        listService.getUpcomingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiUpComingMovies.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }

        })

    }

    init {
        listService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiPopularMovies.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }

        })
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
