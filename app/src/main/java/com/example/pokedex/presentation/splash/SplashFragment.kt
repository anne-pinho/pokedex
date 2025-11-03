package com.example.pokedex.presentation.splash
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R


class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onResume() {
        super.onResume()

        view?.postDelayed({
            findNavController().navigate(R.id.pokemonListFragment)
        }, 1200)
    }
}
