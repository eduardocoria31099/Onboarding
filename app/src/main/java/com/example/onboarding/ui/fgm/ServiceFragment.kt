package com.example.onboarding.ui.fgm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.onboarding.databinding.FragmentServiceBinding
import com.example.core.domain.Result
import com.example.onboarding.ui.adapter.CharacterAdapter
import com.example.onboarding.viewmodel.CharacterViewModel
import com.example.utils.ExtendedFunctions.collect
import com.example.utils.ExtendedFunctions.gone
import com.example.utils.ExtendedFunctions.show
import com.example.utils.ExtendedFunctions.toast
import kotlinx.coroutines.launch

class ServiceFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by activityViewModels()
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var listCharacter: List<Result>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() = lifecycleScope.launch {
        setAdapter()
        setRecycler()
        viewModel.getCharacter()
        setFlows()
        setListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setFlows() {
        collect(viewModel.state) {

            if (it.loading) {
                binding.pbLoading.show()
            } else {
                binding.pbLoading.gone()
                characterAdapter.addList(it.character)
                binding.tvNumberPeople.text = "Total ${characterAdapter.itemCount} person"
            }

            it.character.let { character ->
                listCharacter = character
            }
        }
    }

    private fun setAdapter() {
        characterAdapter = CharacterAdapter(emptyList()) { character ->
            requireContext().toast("${character.name}")
        }
    }

    private fun setRecycler() = with(binding) {
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners() {
        binding.apply {
            txtCharacter.addTextChangedListener {
                val newLis = listCharacter.filter { character ->
                    character.name!!.lowercase().contains(it.toString().lowercase())
                }
                characterAdapter.updateList(newLis)
                tvNumberPeople.text = "Total ${characterAdapter.itemCount} person"
            }
            tvNumberPeople.text = "Total ${characterAdapter.itemCount} person"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
