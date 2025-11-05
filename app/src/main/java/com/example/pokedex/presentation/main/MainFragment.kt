package com.example.pokedex.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentMainBinding
import com.example.pokedex.domain.model.Pokemon
import androidx.core.view.isVisible
import com.example.pokedex.presentation.main.adapter.PokemonAdapter


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PokemonAdapter
    private val viewModel: PokemonListViewModel by viewModels {
        ViewModelFactory() // vou te passar essa factory depois
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PokemonAdapter()
        binding.recyclerPokemon.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPokemon.adapter = adapter


        observeState()
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.progressBar.isVisible = state.isLoading

                state.error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }

                adapter.submitList(state.pokemons)
            }
        }
    }
}
