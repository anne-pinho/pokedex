package com.example.pokedex.domain.usecase

import com.example.pokedex.domain.model.FavoritePokemon
import com.example.pokedex.domain.repository.FavoriteRepository

class GetFavoritesUseCase(private val repo: FavoriteRepository) {
    operator fun invoke() = repo.getAllFavorites()
}

class AddFavoriteUseCase(private val repo: FavoriteRepository) {
    suspend operator fun invoke(pokemon: FavoritePokemon) = repo.addFavorite(pokemon)
}

class RemoveFavoriteUseCase(private val repo: FavoriteRepository) {
    suspend operator fun invoke(pokemon: FavoritePokemon) = repo.removeFavorite(pokemon)
}

class IsFavoriteUseCase(private val repo: FavoriteRepository) {
    suspend operator fun invoke(id: Int) = repo.isFavorite(id)
}
