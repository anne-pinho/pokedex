package com.example.pokedex.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.domain.model.Pokemon

class PokemonAdapter : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) =
            oldItem == newItem
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {
            binding.txtPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.txtPokemonNumber.text = "#${pokemon.id}"

            Glide.with(binding.root.context)
                .load(pokemon.imageUrl)
                .into(binding.imgPokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
