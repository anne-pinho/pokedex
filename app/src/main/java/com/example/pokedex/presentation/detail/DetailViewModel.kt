package com.example.pokedex.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _number = MutableLiveData<String>()
    val number: LiveData<String> = _number

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    private val _types = MutableLiveData<List<String>>()
    val types: LiveData<List<String>> = _types

    private val _height = MutableLiveData<Int>()
    val height: LiveData<Int> = _height

    private val _weight = MutableLiveData<Int>()
    val weight: LiveData<Int> = _weight

    private val _hp = MutableLiveData<Int>()
    val hp: LiveData<Int> = _hp

    private val _attack = MutableLiveData<Int>()
    val attack: LiveData<Int> = _attack

    private val _defense = MutableLiveData<Int>()
    val defense: LiveData<Int> = _defense

    fun setPokemonData(
        name: String,
        number: String,
        imageUrl: String,
        types: List<String>,
        height: Int,
        weight: Int,
        hp: Int,
        attack: Int,
        defense: Int
    ) {
        _name.value = name
        _number.value = number
        _imageUrl.value = imageUrl
        _types.value = types
        _height.value = height
        _weight.value = weight
        _hp.value = hp
        _attack.value = attack
        _defense.value = defense
    }
}
