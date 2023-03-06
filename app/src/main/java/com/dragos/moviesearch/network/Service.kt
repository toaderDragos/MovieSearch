package com.dragos.moviesearch.network

import com.dragos.moviesearch.data.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieInfoService {
    @GET(ApiConstant.SHOW_QUERY_PATH)
    suspend fun getMovies(@Query("q") query: String): ArrayList<Movie>
}

object MovieListNetwork {
    val apiService: MovieInfoService =
        Retrofit.Builder()
            .baseUrl(ApiConstant.MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpSingleton.instance)
            .build().create(MovieInfoService::class.java)
}
