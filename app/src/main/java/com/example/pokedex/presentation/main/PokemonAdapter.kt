package com.example.pokedex.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.domain.model.Pokemon

class PokemonAdapter(
    private val pokemonList: List<Pokemon>,
    private val onClick: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        holder.binding.txtPokemonName.text = pokemon.name
        holder.binding.txtPokemonNumber.text = "#${pokemon.number}"

        Glide.with(holder.itemView)
            .load(pokemon.imageUrl)
            .into(holder.binding.imgPokemon)

        holder.itemView.setOnClickListener {
            onClick(pokemon)
        }
    }

    override fun getItemCount() = pokemonList.size
}
