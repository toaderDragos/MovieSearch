package com.dragos.moviesearch.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.dragos.moviesearch.R
import com.dragos.moviesearch.databinding.FragmentWelcomeBinding
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator


class WelcomeFragment : Fragment() {

    lateinit var star: ImageView
    lateinit var TV: ImageView
    lateinit var couch: ImageView

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentWelcomeBinding
        = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        val navController = NavHostFragment.findNavController(this)

        binding.startButton.setOnClickListener {
            navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSearchFragment())
            }

        star = binding.star
        TV = binding.tvView
        couch = binding.couchView

        translaterRL()   // right to left translator
        translaterLR()
        return binding.root
    }

    private fun translaterRL() {
        val animator = ObjectAnimator.ofFloat(TV, View.TRANSLATION_X, 900f)
        animator.repeatCount = 0
        animator.duration = 2000
        // animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun translaterLR() {
        val animator = ObjectAnimator.ofFloat(couch, View.TRANSLATION_X, -1100f)
        animator.repeatCount = 0
        animator.duration = 2000
        // animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    // disabling the button when animating
    private fun disableViewDuringAnimation(view: View, animator: ObjectAnimator) {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }
}