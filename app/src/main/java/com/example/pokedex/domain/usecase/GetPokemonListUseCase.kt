package com.example.pokedex.domain.usecase

import com.example.pokedex.domain.repository.PokemonRepository

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke() = repository.getPokemonList()
}
