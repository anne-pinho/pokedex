package com.example.pokedex.data.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<TypeSlot>,
    val height: Int,
    val weight: Int,
    val stats: List<StatSlot>
)

data class Sprites(
    val front_default: String?
)

data class TypeSlot(
    val slot: Int,
    val type: PokemonType
)

data class PokemonType(
    val name: String
)

data class StatSlot(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)
