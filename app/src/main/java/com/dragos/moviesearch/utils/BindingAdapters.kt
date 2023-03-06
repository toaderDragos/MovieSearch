package com.dragos.moviesearch.utils

import android.os.Build.VERSION_CODES.M
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.dragos.moviesearch.R
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragos.moviesearch.viewmodel.MovieApiStatus

@BindingAdapter("showPhoto")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("movieApiStatus")
fun bindStatus(statusImageView: ImageView, status: MovieApiStatus?) {
    when (status) {
        MovieApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MovieApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        else -> {statusImageView.visibility = View.GONE}
    }}


