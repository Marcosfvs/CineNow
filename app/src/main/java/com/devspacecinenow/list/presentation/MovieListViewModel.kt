package com.devspacecinenow.list.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.CineNowApplication
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import java.net.UnknownHostException

class MovieListViewModel(
    private val repository: MovieListRepository
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow(MovieListUiState())
    private val _uiTopRatedMovies = MutableStateFlow(MovieListUiState())
    private val _uiUpComingMovies = MutableStateFlow(MovieListUiState())
    private val _uiPopularMovies = MutableStateFlow(MovieListUiState())

    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying
    val uiTopRatedMovies: StateFlow<MovieListUiState> = _uiTopRatedMovies
    val uiUpComingMovies: StateFlow<MovieListUiState> = _uiUpComingMovies
    val uiPopularMovies: StateFlow<MovieListUiState> = _uiPopularMovies

    init {
        fetchNowPlaying()
        fetchTopRatedMovies()
        fetchUpComing()
        fetchPopularMovies()
    }

    private fun fetchNowPlaying() {
        _uiNowPlaying.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNowPlaying()
            if (result.isSuccess) {
                val movies = result.getOrNull()
                if (movies != null) {
                    val movieUiDataList = movies.map { MovieDTO ->
                        MovieUiData(
                            id = MovieDTO.id,
                            title = MovieDTO.title,
                            overview = MovieDTO.overview,
                            image = MovieDTO.image
                        )
                    }
                    _uiNowPlaying.value = MovieListUiState(list = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiNowPlaying.value = MovieListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )

                } else {
                    _uiNowPlaying.value = MovieListUiState(isError = true)
                }
            }
        }
    }

    private fun fetchTopRatedMovies() {
        _uiTopRatedMovies.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getTopRated()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map { MovieDTO ->
                        MovieUiData(
                            id = MovieDTO.id,
                            title = MovieDTO.title,
                            overview = MovieDTO.overview,
                            image = MovieDTO.posterFullPath
                        )
                    }
                    _uiTopRatedMovies.value = MovieListUiState(list = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiTopRatedMovies.value = MovieListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )

                } else {
                    _uiTopRatedMovies.value = MovieListUiState(isError = true)
                }
            }
        }
    }


    private fun fetchUpComing() {
        _uiUpComingMovies.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getUpComing()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map { MovieDTO ->
                        MovieUiData(
                            id = MovieDTO.id,
                            title = MovieDTO.title,
                            overview = MovieDTO.overview,
                            image = MovieDTO.posterFullPath
                        )
                    }
                    _uiUpComingMovies.value = MovieListUiState(list = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiUpComingMovies.value = MovieListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )

                } else {
                    _uiUpComingMovies.value = MovieListUiState(isError = true)
                }
            }
        }

    }

    private fun fetchPopularMovies() {
        _uiPopularMovies.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getPopular()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map { MovieDTO ->
                        MovieUiData(
                            id = MovieDTO.id,
                            title = MovieDTO.title,
                            overview = MovieDTO.overview,
                            image = MovieDTO.posterFullPath
                        )
                    }
                    _uiPopularMovies.value = MovieListUiState(list = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiPopularMovies.value = MovieListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )

                } else {
                    _uiPopularMovies.value = MovieListUiState(isError = true)
                }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MovieListViewModel(
                   repository = (application as CineNowApplication).repository
                ) as T
            }
        }

    }
}
