package com.example.pokedex.data.repository

import com.example.pokedex.data.local.FavoritePokemonDao
import com.example.pokedex.domain.model.FavoritePokemon
import com.example.pokedex.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(private val dao: FavoritePokemonDao) : FavoriteRepository {

    override fun getAllFavorites(): Flow<List<FavoritePokemon>> = dao.getAllFavorites()

    override suspend fun addFavorite(pokemon: FavoritePokemon) = dao.insert(pokemon)

    override suspend fun removeFavorite(pokemon: FavoritePokemon) = dao.delete(pokemon)

    override suspend fun isFavorite(id: Int): Boolean = dao.isFavorite(id)
}
