package com.example.pokedex.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.FavoritePokemon
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetail? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonDetailState())
    val state: StateFlow<PokemonDetailState> = _state

    fun loadPokemon(name: String) {
        viewModelScope.launch {
            _state.value = PokemonDetailState(isLoading = true)

            try {
                val pokemon = getPokemonDetailUseCase(name)
                val favorite = isFavoriteUseCase(pokemon.id)
                _state.value = PokemonDetailState(
                    pokemon = pokemon,
                    isFavorite = favorite
                )
            } catch (e: Exception) {
                _state.value = PokemonDetailState(error = e.message)
            }
        }
    }

    fun toggleFavorite() {
        val current = _state.value
        val pokemon = current.pokemon ?: return

        viewModelScope.launch {
            if (current.isFavorite) {
                removeFavoriteUseCase(FavoritePokemon(pokemon.id, pokemon.name, pokemon.imageUrl))
                _state.value = current.copy(isFavorite = false)
            } else {
                addFavoriteUseCase(FavoritePokemon(pokemon.id, pokemon.name, pokemon.imageUrl))
                _state.value = current.copy(isFavorite = true)
            }
        }
    }
}
