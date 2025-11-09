package com.example.pokedex.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetail? = null,
    val error: String? = null
)

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonDetailState())
    val state: StateFlow<PokemonDetailState> = _state

    fun loadPokemon(name: String) {
        viewModelScope.launch {
            // Limpa estado antigo antes de buscar
            _state.value = PokemonDetailState(isLoading = true, pokemon = null, error = null)

            try {
                val pokemon = getPokemonDetailUseCase(name)
                _state.value = PokemonDetailState(pokemon = pokemon)
            } catch (e: Exception) {
                _state.value = PokemonDetailState(error = e.message)
            }
        }
    }
}
