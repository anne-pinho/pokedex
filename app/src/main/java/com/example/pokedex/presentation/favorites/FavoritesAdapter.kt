package com.example.pokedex.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ItemFavoritePokemonBinding
import com.example.pokedex.domain.model.FavoritePokemon

class FavoritesAdapter(
    private val onClick: (FavoritePokemon) -> Unit
) : ListAdapter<FavoritePokemon, FavoritesAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoritePokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemFavoritePokemonBinding,
        private val onClick: (FavoritePokemon) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: FavoritePokemon) {
            binding.txtFavoriteName.text = pokemon.name

            Glide.with(binding.root)
                .load(pokemon.imageUrl)
                .into(binding.imgFavorite)

            binding.root.setOnClickListener { onClick(pokemon) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<FavoritePokemon>() {
            override fun areItemsTheSame(oldItem: FavoritePokemon, newItem: FavoritePokemon) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FavoritePokemon, newItem: FavoritePokemon) =
                oldItem == newItem
        }
    }
}
