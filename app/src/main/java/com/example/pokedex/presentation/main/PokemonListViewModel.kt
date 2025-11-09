package com.example.pokedex.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.example.pokedex.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state

    private var _allPokemons: MutableList<Pokemon> = mutableListOf()

    init {
        loadPokemons()
    }

    fun loadPokemons() {
        viewModelScope.launch {
            _state.value = PokemonListState(isLoading = true)

            try {
                val pokemons = getPokemonListUseCase()

                // Inicialmente mostra só nomes e imagens
                _allPokemons = pokemons.toMutableList()
                _state.value = PokemonListState(pokemons = _allPokemons)

                // Agora carrega tipos em paralelo
                _allPokemons.map { pokemon ->
                    async {
                        val detail = try { getPokemonDetailUseCase(pokemon.name) } catch (e: Exception) { null }
                        detail?.types?.let { types ->
                            val index = _allPokemons.indexOfFirst { it.id == pokemon.id }
                            if (index >= 0) {
                                _allPokemons[index] = _allPokemons[index].copy(types = types)
                                // Atualiza a lista filtrada (ou completa)
                                _state.value = _state.value.copy(pokemons = _allPokemons)
                            }
                        }
                    }
                }.forEach { it.await() }

            } catch (e: Exception) {
                _state.value = PokemonListState(error = e.message ?: "Erro inesperado")
            }
        }
    }

    fun filterPokemons(typeFilter: String = "", genFilter: String = "", searchQuery: String = "") {
        val filtered = _allPokemons.filter { pokemon ->
            val matchesType = typeFilter.isEmpty() || pokemon.types.contains(typeFilter)
            val matchesGen = genFilter.isEmpty() || getGeneration(pokemon.id) == genFilter
            val matchesSearch = searchQuery.isEmpty() || pokemon.name.contains(searchQuery, ignoreCase = true)
            matchesType && matchesGen && matchesSearch
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
            else -> ""
        }
    }
}
