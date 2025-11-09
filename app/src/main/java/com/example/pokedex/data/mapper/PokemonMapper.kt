package com.example.pokedex.data.mapper

import com.example.pokedex.data.model.PokemonDetailResponse
import com.example.pokedex.data.model.PokemonResult
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail

fun PokemonResult.toPokemon(): Pokemon {
    val number = url
        .trimEnd('/')
        .split("/")
        .last()
        .toInt()

    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"

    return Pokemon(
        id = number,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = imageUrl
    )
}

fun PokemonDetailResponse.toPokemonDetail(): PokemonDetail {
    val imageUrl = sprites.other?.officialArtwork?.front_default ?: sprites.front_default ?: ""

    val types = types.map { it.type.name.replaceFirstChar { c -> c.uppercase() } }

    val hp = stats.find { it.stat.name == "hp" }?.base_stat ?: 0
    val attack = stats.find { it.stat.name == "attack" }?.base_stat ?: 0
    val defense = stats.find { it.stat.name == "defense" }?.base_stat ?: 0

    return PokemonDetail(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = imageUrl,
        types = types,
        height = height,
        weight = weight,
        hp = hp,
        attack = attack,
        defense = defense
    )
}

