package com.example.pokedex.presentation.main

import com.example.pokedex.domain.model.Pokemon

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val error: String? = null
)
