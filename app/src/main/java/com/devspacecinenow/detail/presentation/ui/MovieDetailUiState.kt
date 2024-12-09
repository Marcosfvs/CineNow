package com.devspacecinenow.detail.presentation.ui

import com.devspacecinenow.common.data.remote.MovieDto

sealed class MovieDetailUiState {
    data object Loading : MovieDetailUiState()
    data class Success(val movie: MovieDto) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
}