package com.example.pokedex.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments()

        // Preenche o ViewModel com os dados recebidos
        viewModel.setPokemonData(
            name = args.getString("name") ?: "",
            number = args.getString("number") ?: "",
            imageUrl = args.getString("imageUrl") ?: "",
            types = args.getStringArrayList("types") ?: emptyList(),
            height = args.getInt("height"),
            weight = args.getInt("weight"),
            hp = args.getInt("hp"),
            attack = args.getInt("attack"),
            defense = args.getInt("defense")
        )

        // Observa e preenche UI
        viewModel.name.observe(viewLifecycleOwner) { binding.txtPokemonName.text = it }
        viewModel.number.observe(viewLifecycleOwner) { binding.txtPokemonID.text = it }
        viewModel.height.observe(viewLifecycleOwner) { binding.txtPokemonHeight.text = "Height: $it m" }
        viewModel.weight.observe(viewLifecycleOwner) { binding.txtPokemontWeight.text = "Weight: $it kg" }
        viewModel.hp.observe(viewLifecycleOwner) { binding.txtHP.text = "HP: $it" }
        viewModel.attack.observe(viewLifecycleOwner) { binding.txtAttack.text = "Attack: $it" }
        viewModel.defense.observe(viewLifecycleOwner) { binding.txtDefense.text = "Defense: $it" }

        viewModel.types.observe(viewLifecycleOwner) {
            binding.txtPokemonType.text = "Type: ${it.joinToString(" / ")}"
        }

        viewModel.imageUrl.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it)
                .into(binding.imgPokemon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
