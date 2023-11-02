package com.dragos.moviesearch.ui.main

// Necessary imports
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

/**
 * A Fragment that shows the detailed view of a selected movie.
 */
class DetailFragment : Fragment() {
    // Arguments passed from the navigation component
    private val navigationArgs: DetailFragmentArgs by navArgs()
    
    // Movie object that will be initialized with the movie retrieved from the viewModel
    lateinit var movie: Movie
    
    // ViewModel that is shared with the activity
    private val viewModel: MainViewModel by activityViewModels()

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment with data binding
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        // Retrieve and assign the selected movie based on the passed ID
        val id = navigationArgs.selectedMovie
        movie = viewModel.retrieveMovie(id)!!

        // Bind the movie image or set a placeholder if the image is not available
        try {
            bindImage(binding.imageView2, movie.show.image.medium)
        } catch (e: Exception) {
            binding.imageView2.setImageResource(R.drawable.ic_broken_image)
        }

        // Bind the non-null movie details or provide a default value if null
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

        // Parse HTML content if available for the movie summary
        movie.show.summary?.let { summary ->
            binding.detailSummary.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(summary, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(summary)
            }
        } ?: run {
            binding.detailSummary.text = getString(R.string.no_summary)
        }

        // Bind network and country information, use a placeholder if unavailable
        movie.show.network?.let { network ->
            binding.detailNetwork.text = network.name
            binding.detailCountry.text = network.country.name ?: " - "
        } ?: run {
            binding.detailNetwork.text = " - "
            binding.detailCountry.text = " - "
        }

        // Set up a listener on the website button to open the movie's official site
        binding.website.setOnClickListener {
            linkButtons(binding.website, movie.show.officialSite)
        }

        return binding.root
    }

    /**
     * Binds a non-null value to the given TextView or sets it to a replacement string if null.
     */
    private fun bindNonNullValue(bindObjText: TextView, movieDetail: String?, replacement: String) {
        bindObjText.text = movieDetail ?: replacement
    }

    /**
     * Attaches a listener to a button that opens a provided URL in a web browser.
     * Shows a toast if the URL is null.
     */
    private fun linkButtons(button: Button, link: String?) {
        button.setOnClickListener {
            link?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            } ?: Toast.makeText(context, getString(R.string.linkno), Toast.LENGTH_SHORT).show()
        }
    }
}
