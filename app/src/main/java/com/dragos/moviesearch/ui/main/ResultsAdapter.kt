package com.dragos.moviesearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dragos.moviesearch.R
import com.dragos.moviesearch.data.Movie
import com.dragos.moviesearch.databinding.MovieItemBinding
import com.dragos.moviesearch.utils.bindImage

class ResultsAdapter(private val onItemClicked: (String) -> Unit) : ListAdapter<Movie, ResultsAdapter.MovieHolder>(DiffCallback) {

    var info: List<Movie> = listOf()

    class MovieHolder(private var binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie){
            binding.movie = movie
            binding.itemTitle.text = movie.show.name

            if (movie.show.genres.joinToString(separator = " ") == ""){
                binding.itemGenres.text = "Not specified"
            } else {
                binding.itemGenres.text = movie.show.genres.joinToString(separator = " ")
            }

            if (movie.show.rating.average == null){
                binding.itemScore.text = "0.0"
            } else {
                binding.itemScore.text = movie.show.rating.average
            }

            try {
                bindImage(binding.movieImg, movie.show.image.medium)
                println("dra the image link is here " + movie.show.image.medium )
            } catch (e: Exception) {
                binding.movieImg.setImageResource(R.drawable.ic_broken_image)
            }
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(info[position])
        val movie = info[position]
        holder.itemView.setOnClickListener {
            onItemClicked(movie.show.id)
        }
    }

    override fun getItemCount() = info.size

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.show.id == newItem.show.id
        }
    }
}

