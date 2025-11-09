package com.example.pokedex.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.domain.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state

    private var allPokemons: List<Pokemon> = emptyList()

    init {
        loadPokemons()
    }

    fun loadPokemons() {
        viewModelScope.launch {
            _state.value = PokemonListState(isLoading = true)
            try {
                // 1️⃣ Lista básica
                val pokemons = getPokemonListUseCase()

                // 2️⃣ Preencher tipos usando coroutines paralelas
                val pokemonsWithTypes = pokemons.map { pokemon ->
                    async {
                        val detail = getPokemonDetailUseCase(pokemon.name)
                        pokemon.copy(types = detail.types.map { it.lowercase() })
                    }
                }.awaitAll()

                // 3️⃣ Guardar lista completa
                allPokemons = pokemonsWithTypes

                // 4️⃣ Atualizar estado
                _state.value = PokemonListState(pokemons = pokemonsWithTypes)
            } catch (e: Exception) {
                _state.value = PokemonListState(error = e.message ?: "Erro inesperado")
            }
        }
    }

    fun filterPokemons(typeFilter: String = "", genFilter: String = "") {
        val filtered = allPokemons.filter { pokemon ->
            val matchesType = typeFilter.isEmpty() || pokemon.types.contains(typeFilter.lowercase())
            val matchesGen = genFilter.isEmpty() || getGeneration(pokemon.id) == genFilter
            matchesType && matchesGen
        }
        _state.value = _state.value.copy(pokemons = filtered)
    }

    private fun getGeneration(id: Int): String {
        return when (id) {
            in 1..151 -> "Gen I"
            in 152..251 -> "Gen II"
            in 252..386 -> "Gen III"
            in 387..493 -> "Gen IV"
            in 494..649 -> "Gen V"
            in 650..721 -> "Gen VI"
            in 722..809 -> "Gen VII"
            in 810..898 -> "Gen VIII"
            else -> "Unknown"
        }
    }
}
