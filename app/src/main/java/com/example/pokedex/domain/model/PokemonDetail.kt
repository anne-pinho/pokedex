package com.example.pokedex.domain.model

data class PokemonDetail(
    val name: String,
    val id: Int,
    val imageUrl: String,
    val types: List<String>,
    val height: Int,
    val weight: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int
)
