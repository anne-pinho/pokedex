package com.example.pokedex.data.local

import androidx.room.*
import com.example.pokedex.domain.model.FavoritePokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {

    @Query("SELECT * FROM favorite_pokemon")
    fun getAllFavorites(): Flow<List<FavoritePokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: FavoritePokemon)

    @Delete
    suspend fun delete(pokemon: FavoritePokemon)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemon WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
