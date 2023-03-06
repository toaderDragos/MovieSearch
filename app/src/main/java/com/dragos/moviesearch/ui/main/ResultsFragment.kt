package com.dragos.moviesearch.ui.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dragos.moviesearch.databinding.FragmentResultsBinding
import com.dragos.moviesearch.viewmodel.MainViewModel

class ResultsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.query.text = viewModel.query.value.toString()

        binding.magnifierButton2.setOnClickListener {
            val rawInputQuery: String = binding.searchMovie.text.toString()
            viewModel.updateQuery(rawInputQuery)
            viewModel.getMovieInfo()
            this.view?.let { it1 -> hideKeyboardFrom(requireActivity(), it1) }
            binding.query.text = viewModel.query.value.toString()
        }

         val myadapter = ResultsAdapter{
             val action = ResultsFragmentDirections.actionResultsFragmentToDetailFragment(it)
             this.findNavController().navigate(action)
         }

        viewModel.moviesList.observe(viewLifecycleOwner) {
            if (it != null){
                println("dra avem urmatoarele filme atasate la adaptor: " + it)
                myadapter.info = it
                myadapter.notifyDataSetChanged()
            } else {
                println("dra does this listen to anything?")
            }
        }

        binding.resultsRecyclerview.adapter = myadapter
        binding.resultsRecyclerview.layoutManager = GridLayoutManager(context, 2)
        return binding.root
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
