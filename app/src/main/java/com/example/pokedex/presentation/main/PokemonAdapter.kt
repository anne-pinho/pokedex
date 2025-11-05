package com.example.pokedex.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.domain.model.Pokemon

class PokemonAdapter(
    private val onItemClick: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val pokemonList = mutableListOf<Pokemon>()

    fun setItems(list: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(list)
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {
            binding.txtPokemonName.text = pokemon.name
            binding.txtPokemonNumber.text = "#${pokemon.number}"

            Glide.with(binding.imgPokemon.context)
                .load(pokemon.imageUrl)
                .into(binding.imgPokemon)

            binding.root.setOnClickListener { onItemClick(pokemon) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size
}
