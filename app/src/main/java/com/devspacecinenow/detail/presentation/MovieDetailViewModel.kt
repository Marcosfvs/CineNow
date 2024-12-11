package com.devspacecinenow.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.remote.RetrofitClient
import com.devspacecinenow.detail.data.DetailService
import com.devspacecinenow.detail.presentation.ui.MovieDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val detailService: DetailService,

    ) : ViewModel() {
    private val _uiDetailMovie = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiDetailMovie: StateFlow<MovieDetailUiState> = _uiDetailMovie

    fun fetchMovieDetail(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiDetailMovie.value = MovieDetailUiState.Loading
       try{
                val response = detailService.getMovieById(movieId)
                if (response.isSuccessful) {
                    _uiDetailMovie.value = MovieDetailUiState.Success(response.body()!!)
                } else {
                    _uiDetailMovie.value = MovieDetailUiState.Error ("Request Error")
                }
            } catch (e: Exception){
                _uiDetailMovie.value = MovieDetailUiState.Error ("Network Error")
            }
        }
    }

    fun cleanMovieID() {
        viewModelScope.launch {
            delay(2000)
            _uiDetailMovie.value = MovieDetailUiState.Loading
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return MovieDetailViewModel(
                    detailService
                ) as T
            }

        }
    }



}