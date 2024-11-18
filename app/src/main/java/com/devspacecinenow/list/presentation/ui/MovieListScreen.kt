package com.devspacecinenow.list.presentation.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.ApiService
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.list.presentation.MovieListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieListScreen(
    navController: NavHostController,
    viewModel: MovieListViewModel
){
    val nowPlayingMovies by viewModel.uiNowPlaying.collectAsState()
    val topRatedMovies by viewModel.uiTopRatedMovies.collectAsState()
    val upComingMovies by viewModel.uiUpComingMovies.collectAsState()
    val popularMovies by viewModel.uiPopularMovies.collectAsState()

    MovieListContent(
        topRatedMovies = topRatedMovies,
        nowPlayingMovies = nowPlayingMovies,
        upComingMovies = upComingMovies,
        popularMovies = popularMovies
    ) { itemClicked ->
        navController.navigate(route= "movieDetail/${itemClicked.id}")

    }

}

@Composable
private fun MovieListContent(
    topRatedMovies: List<MovieDTO>,
    nowPlayingMovies: List<MovieDTO>,
    upComingMovies: List<MovieDTO>,
    popularMovies: List<MovieDTO>,
    onclick: (MovieDTO) -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            text = "Cine Now"
        )

        MovieSession(
            label = "Up Coming",
            movieList = upComingMovies,
            onClick = onclick
        )

        MovieSession(
            label = "Now Playing",
            movieList = nowPlayingMovies,
            onClick = onclick
        )

        MovieSession(
            label = "Top Rated",
            movieList = topRatedMovies,
            onClick = onclick
        )

        MovieSession(
            label = "Popular",
            movieList = popularMovies,
            onClick = onclick
        )

    }
}

@Composable
private fun MovieSession(
    label: String,
    movieList: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            fontSize = 24.sp,
            text = label,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(8.dp))
        MovieList(movieList = movieList, onClick = onClick)
    }
}

@Composable
private fun MovieList(
    movieList: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit
) {
    LazyRow {
        items(movieList) {
            MovieItem(
                movieDTO = it,
                onClick = onClick
            )
        }
    }

}

@Composable
private fun MovieItem(
    movieDTO: MovieDTO,
    onClick: (MovieDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onClick.invoke(movieDTO)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(end = 4.dp)
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieDTO.posterFullPath,
            contentDescription = "${movieDTO.title} Poster Image"
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            text = movieDTO.title
        )
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieDTO.overview
        )
    }

}