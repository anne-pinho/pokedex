package com.example.pokedex.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.data.remote.RetrofitInstance
import com.example.pokedex.data.repository.PokemonRepositoryImpl
import com.example.pokedex.domain.usecase.GetPokemonListUseCase

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val api = RetrofitInstance.api
        val repository = PokemonRepositoryImpl(api)

        // use case
        val getPokemonListUseCase = GetPokemonListUseCase(repository)

        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            return PokemonListViewModel(getPokemonListUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
