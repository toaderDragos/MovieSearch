package com.dragos.moviesearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dragos.moviesearch.R
import com.dragos.moviesearch.databinding.FragmentSearchBinding
import com.dragos.moviesearch.viewmodel.MainViewModel

class SearchFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        binding.magnifierButton.setOnClickListener {
            val rawInputQuery: String = binding.searchMovie.text.toString()
            viewModel.updateQuery(rawInputQuery)
            viewModel.getMovieInfo()
            view?.findNavController()?.navigate(SearchFragmentDirections.actionSearchFragmentToResultsFragment())
        }

        return binding.root
    }


}