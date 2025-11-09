package com.example.pokedex.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.data.remote.RetrofitInstance
import com.example.pokedex.data.repository.PokemonRepositoryImpl
import com.example.pokedex.domain.usecase.GetPokemonDetailUseCase

class DetailViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = RetrofitInstance.api
        val repository = PokemonRepositoryImpl(api)

        val getPokemonDetailUseCase = GetPokemonDetailUseCase(repository)

        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(getPokemonDetailUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
