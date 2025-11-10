package com.example.pokedex.domain.repository

import kotlinx.coroutines.flow.Flow
import com.example.pokedex.domain.model.FavoritePokemon

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<FavoritePokemon>>
    suspend fun addFavorite(pokemon: FavoritePokemon)
    suspend fun removeFavorite(pokemon: FavoritePokemon)
    suspend fun isFavorite(id: Int): Boolean
}
