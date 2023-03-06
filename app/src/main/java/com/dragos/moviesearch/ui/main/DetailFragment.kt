package com.dragos.moviesearch.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.dragos.moviesearch.R
import com.dragos.moviesearch.data.Movie
import com.dragos.moviesearch.databinding.FragmentDetailBinding
import com.dragos.moviesearch.utils.bindImage
import com.dragos.moviesearch.viewmodel.MainViewModel


class DetailFragment : Fragment() {
    private val navigationArgs: DetailFragmentArgs by navArgs()
    lateinit var movie: Movie
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater,
            com.dragos.moviesearch.R.layout.fragment_detail,
            container,
            false
        )

        val id = navigationArgs.selectedMovie
        movie = viewModel.retrieveMovie(id)!!

        try {
            bindImage(binding.imageView2, movie.show.image.medium)
        } catch (e: Exception) {
            binding.imageView2.setImageResource(R.drawable.ic_broken_image)
        }

        bindNonNullValue(binding.detailMovieTitle, movie.show.name, "Error")
        bindNonNullValue(binding.detailPremiered, movie.show.premiered, "No date")
        bindNonNullValue(binding.showLenght, movie.show.runTime, "0")
        bindNonNullValue(
            binding.detailGenre,
            movie.show.genres.joinToString(separator = ", "),
            "Unspecified"
        )
        bindNonNullValue(binding.detailScore2, movie.show.rating.average, "0.0")
        bindNonNullValue(binding.detailLanguage, movie.show.language, " - ")

        // Taking Html from API as it is - verify null
        if (movie.show.summary != null) {
            val unparsedSummary = movie.show.summary
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.detailSummary.text =
                    Html.fromHtml(unparsedSummary, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                binding.detailSummary.text = Html.fromHtml(unparsedSummary)
            }
        } else {
            binding.detailSummary.text = getString(R.string.no_summary)
        }

        // Some results are incomplete (for example the network branch(with the country name inside) is non- existent in some entries)
        if (movie.show.network != null) {
            binding.detailNetwork.text = movie.show.network.name
            if (movie.show.network.country.name != null) {
                binding.detailCountry.text = movie.show.network.country.name
            } else {
                binding.detailCountry.text = " - "
            }
        } else {
            binding.detailNetwork.text = " - "
        }

        binding.website.setOnClickListener {
            linkButtons(binding.website, movie.show.officialSite)
        }

        return binding.root
    }

    private fun bindNonNullValue(bindObjText: TextView, movieDetail: String?, replacement: String) {
        if (movieDetail != null) {
            bindObjText.text = movieDetail
        } else {
            bindObjText.text = replacement
        }
    }

    fun linkButtons(button: Button, link: String?) {

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (link != null) {
                    val url = link
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                } else {
                    Toast.makeText(context, getString(R.string.linkno), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}