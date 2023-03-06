package com.dragos.moviesearch.viewmodel

import androidx.lifecycle.*
import com.dragos.moviesearch.data.Movie
import com.dragos.moviesearch.repository.DataRepository
import kotlinx.coroutines.launch
enum class MovieApiStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel() {

    private val repository: DataRepository = DataRepository
    private var _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus> = _status

    private var _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _navigateToSelectedMovie : MutableLiveData<Movie> = MutableLiveData()
    val navigateToSelectedMovie: MutableLiveData<Movie>
        get() = _navigateToSelectedMovie

    fun getMovieInfo() {
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            try {
                _moviesList.value = _query.value?.let { repository.getMovieInfo(it)}
                _status.value = MovieApiStatus.DONE
                println("dra here we have the movie list at done: " + _moviesList.value)
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
                _moviesList.value = ArrayList()
            }
        }
    }

    fun updateQuery(rawInputQuery: String) {
        _query.value = rawInputQuery
        println("dra query is: " + _query.value)
    }

    fun retrieveMovie(idArgs: String?): Movie? {
        val movie = moviesList.value?.find {movie: Movie -> movie.show.id == idArgs }
        return movie
    }

}
