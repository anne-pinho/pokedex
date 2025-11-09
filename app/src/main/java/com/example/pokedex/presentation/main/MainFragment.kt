package com.example.pokedex.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentMainBinding
import com.example.pokedex.presentation.main.adapter.PokemonAdapter
import kotlinx.coroutines.flow.collectLatest
import android.graphics.Color

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonListViewModel by viewModels { ViewModelFactory() }
    private lateinit var adapter: PokemonAdapter

    private var selectedType = ""
    private var selectedGen = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupSpinners()
        setupSearch()
        observeState()
    }

    private fun setupRecycler() {
        adapter = PokemonAdapter { pokemon ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                name = pokemon.name,
                number = pokemon.id,
                imageUrl = pokemon.imageUrl
            )
            findNavController().navigate(action)
        }
        binding.recyclerPokemon.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPokemon.adapter = adapter
    }

    private fun setupSpinners() {
        val types = listOf("Tipos", "Fire", "Water", "Grass", "Electric", "Normal")
        val gens = listOf("Geração", "Gen I", "Gen II", "Gen III", "Gen IV", "Gen V", "Gen VI", "Gen VII", "Gen VIII")

        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTypePokemon.adapter = typeAdapter

        val genAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gens)
        genAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenerationPokemon.adapter = genAdapter

        binding.spinnerTypePokemon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedType = if (position == 0) "" else parent?.getItemAtPosition(position).toString()
                viewModel.filterPokemons(typeFilter = selectedType, genFilter = selectedGen)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerGenerationPokemon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedGen = if (position == 0) "" else parent?.getItemAtPosition(position).toString()
                viewModel.filterPokemons(typeFilter = selectedType, genFilter = selectedGen)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSearch() {
// Garantir placeholder visível
        binding.searchFilterPokemon.apply {
            // Define o hint
            queryHint = "Buscar Pokémon"

            // Mantém o hint visível mesmo sem texto digitado
            setQuery("", false)

            // Opcional: muda a cor do placeholder
            val searchEditText = findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.setHintTextColor(Color.GRAY)

            // Listener para filtrar em tempo real
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    val filtered = viewModel.state.value.pokemons.filter { pokemon ->
                        pokemon.name.contains(newText ?: "", ignoreCase = true)
                    }
                    adapter.submitList(filtered)
                    return true
                }
            })
        }

    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                binding.progressBar.isVisible = state.isLoading
                adapter.submitList(state.pokemons)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
