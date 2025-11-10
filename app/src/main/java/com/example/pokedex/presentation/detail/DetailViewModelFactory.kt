package com.example.pokedex.presentation.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.data.local.AppDatabase
import com.example.pokedex.data.remote.RetrofitInstance
import com.example.pokedex.data.repository.FavoriteRepositoryImpl
import com.example.pokedex.data.repository.PokemonRepositoryImpl
import com.example.pokedex.domain.usecase.*

class DetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Repositórios
        val api = RetrofitInstance.api
        val pokemonRepo = PokemonRepositoryImpl(api)

        val db = AppDatabase.getDatabase(context)
        val favoriteRepo = FavoriteRepositoryImpl(db.favoriteDao())

        // Use Cases
        val getPokemonDetailUseCase = GetPokemonDetailUseCase(pokemonRepo)
        val addFavoriteUseCase = AddFavoriteUseCase(favoriteRepo)
        val removeFavoriteUseCase = RemoveFavoriteUseCase(favoriteRepo)
        val isFavoriteUseCase = IsFavoriteUseCase(favoriteRepo)

        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(
                getPokemonDetailUseCase,
                addFavoriteUseCase,
                removeFavoriteUseCase,
                isFavoriteUseCase
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
