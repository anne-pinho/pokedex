package com.example.pokedex.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemon")
data class FavoritePokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
