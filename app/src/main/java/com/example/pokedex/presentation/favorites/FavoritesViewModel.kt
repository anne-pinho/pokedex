package com.example.pokedex.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.FavoritePokemon
import com.example.pokedex.domain.repository.FavoriteRepository
import com.example.pokedex.domain.usecase.GetFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(repo: FavoriteRepository) : ViewModel() {

    private val getFavoritesUseCase = GetFavoritesUseCase(repo)

    private val _favorites = MutableStateFlow<List<FavoritePokemon>>(emptyList())
    val favorites: StateFlow<List<FavoritePokemon>> = _favorites

    init {
        viewModelScope.launch {
            getFavoritesUseCase().collect { list ->
                _favorites.value = list
            }
        }
    }
}
