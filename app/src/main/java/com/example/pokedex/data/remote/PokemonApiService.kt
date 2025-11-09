package com.example.pokedex.data.remote

import com.example.pokedex.data.model.PokemonListResponse
import com.example.pokedex.data.model.PokemonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    // Lista de pokémons
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 200
    ): PokemonListResponse

    // Detalhe do Pokémon por ID ou nome
    @GET("pokemon/{pokemon}")
    suspend fun getPokemonDetail(
        @Path("pokemon") pokemon: String
    ): PokemonDetailResponse
}
