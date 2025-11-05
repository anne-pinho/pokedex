package com.example.pokedex.data.repository

import com.example.pokedex.data.remote.PokemonApiService
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.data.mapper.toPokemonDetail


class PokemonRepositoryImpl(
    private val api: PokemonApiService
) : PokemonRepository {

    override suspend fun getPokemonList(): List<Pokemon> {
        val response = api.getPokemonList()
        return response.results.map { result ->
            val id = extractIdFromUrl(result.url)
            Pokemon(
                id = id.toInt(),
                name = result.name.replaceFirstChar { it.uppercase() },
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
            )
        }
    }


    override suspend fun getPokemonDetail(name: String): PokemonDetail {
        val response = api.getPokemonDetail(name)
        return response.toPokemonDetail()
    }

    private fun extractIdFromUrl(url: String): String {
        return url.trimEnd('/').split("/").last()
    }
}
