package com.example.pokedex.domain.repository

import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail

interface PokemonRepository {
    suspend fun getPokemonList(): List<Pokemon>
    suspend fun getPokemonDetail(name: String): PokemonDetail
}
