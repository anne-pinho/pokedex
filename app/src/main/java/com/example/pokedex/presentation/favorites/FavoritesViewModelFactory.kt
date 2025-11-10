package com.example.pokedex.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.domain.repository.FavoriteRepository

class FavoritesViewModelFactory(private val repo: FavoriteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
