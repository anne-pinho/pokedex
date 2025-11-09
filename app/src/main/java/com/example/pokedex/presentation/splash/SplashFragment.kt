package com.example.pokedex.presentation.splash
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        view?.postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }, 1200)
    }
}



