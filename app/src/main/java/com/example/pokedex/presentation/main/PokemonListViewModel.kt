package com.example.pokedex.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            _state.value = PokemonListState(isLoading = true)

            try {
                val pokemons = getPokemonListUseCase()
                _state.value = PokemonListState(pokemons = pokemons)
            } catch (e: Exception) {
                _state.value = PokemonListState(error = e.message ?: "Erro inesperado")
            }
        }
    }
}
