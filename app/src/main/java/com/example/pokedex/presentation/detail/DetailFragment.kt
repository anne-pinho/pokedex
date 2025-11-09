package com.example.pokedex.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: PokemonDetailViewModel by viewModels {
        DetailViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carrega dados do Pokémon
        viewModel.loadPokemon(args.name)

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.progressBar.isVisible = state.isLoading

                // Só popula a UI quando o Pokémon da API estiver carregado
                if (state.pokemon != null) {
                    val pokemon = state.pokemon
                    binding.txtPokemonName.text = pokemon.name
                    binding.txtPokemonID.text = "#${pokemon.id}"
                    binding.txtPokemonType.text = "Type: ${pokemon.types.joinToString(" / ")}"
                    binding.txtPokemonHeight.text = "Height: ${pokemon.height} m"
                    binding.txtPokemontWeight.text = "Weight: ${pokemon.weight} kg"
                    binding.txtHP.text = "HP: ${pokemon.hp}"
                    binding.txtAttack.text = "Attack: ${pokemon.attack}"
                    binding.txtDefense.text = "Defense: ${pokemon.defense}"

                    Glide.with(binding.root)
                        .load(pokemon.imageUrl)
                        .into(binding.imgPokemon)
                } else {
                    // Limpa os campos enquanto carrega
                    binding.txtPokemonName.text = ""
                    binding.txtPokemonID.text = ""
                    binding.txtPokemonType.text = ""
                    binding.txtPokemonHeight.text = ""
                    binding.txtPokemontWeight.text = ""
                    binding.txtHP.text = ""
                    binding.txtAttack.text = ""
                    binding.txtDefense.text = ""
                    binding.imgPokemon.setImageDrawable(null)
                }

                state.error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
