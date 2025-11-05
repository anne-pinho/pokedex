package com.example.pokedex.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentMainBinding
import com.example.pokedex.domain.model.Pokemon

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inicializa o adapter
        adapter = PokemonAdapter { selectedPokemon ->
            // Aqui vamos abrir o DetailFragment depois
            // Por enquanto apenas imprimir no Log:
            println("Pokemon clicado: ${selectedPokemon.name}")
        }

        // 2. Configura o RecyclerView
        binding.recyclerPokemon.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPokemon.adapter = adapter

        // 3. Adiciona dados fake temporários
        adapter.setItems(
            listOf(
                Pokemon(
                    name = "Bulbasaur",
                    number = 1,
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    types = listOf("Grass", "Poison"),
                    height = 7,
                    weight = 69,
                    hp = 45,
                    attack = 49,
                    defense = 49
                )
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
