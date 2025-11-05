package com.example.pokedex.data.model

data class PokemonListResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)
