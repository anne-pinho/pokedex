package com.example.pokedex.domain.usecase

import com.example.pokedex.domain.repository.PokemonRepository

class GetPokemonDetailUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String) = repository.getPokemonDetail(name)
}
