package com.example.pokedex.presentation.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.collectLatest

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

        val activity = activity as? AppCompatActivity
        activity?.supportActionBar?.show()
        activity?.supportActionBar?.title = args.name
        activity?.supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.loadPokemon(args.name)
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                binding.lottieLoading.isVisible = state.isLoading
                binding.detailContent.isVisible = !state.isLoading

                state.pokemon?.let { pokemon ->
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
