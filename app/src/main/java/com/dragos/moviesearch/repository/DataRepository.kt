package com.dragos.moviesearch.repository

import com.dragos.moviesearch.network.MovieListNetwork

object DataRepository {
    private val api = MovieListNetwork.apiService
    suspend fun getMovieInfo(query: String) = api.getMovies(query)
}