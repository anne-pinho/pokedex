package com.example.pokedex.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.data.local.AppDatabase
import com.example.pokedex.data.repository.FavoriteRepositoryImpl
import com.example.pokedex.databinding.FragmentFavoritesBinding
import com.example.pokedex.domain.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val repository: FavoriteRepository by lazy {
        FavoriteRepositoryImpl(
            AppDatabase.getDatabase(requireContext()).favoriteDao()
        )
    }

    private val viewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory(repository)
    }

    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Agora usamos a toolbar da MainActivity
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Favoritos"
            setDisplayHomeAsUpEnabled(true)
        }

        // Botão de voltar pela toolbar global
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        setupRecycler()
        observeFavorites()
    }

    private fun setupRecycler() {
        adapter = FavoritesAdapter { favoritePokemon ->
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                name = favoritePokemon.name,
                number = favoritePokemon.id,
                imageUrl = favoritePokemon.imageUrl
            )
            findNavController().navigate(action)
        }

        binding.recyclerFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavorites.adapter = adapter
    }

    private fun observeFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { favorites ->
                    adapter.submitList(favorites)
                    binding.recyclerFavorites.isVisible = favorites.isNotEmpty()
                    binding.emptyStateGroup.isVisible = favorites.isEmpty()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
