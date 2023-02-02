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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onboarding.databinding.FragmentServiceBinding
import com.example.onboarding.domain.Result
import com.example.onboarding.ui.adapter.CharacterAdapter
import com.example.onboarding.viewmodel.CharacterViewModel
import com.example.utils.ExtendedFunctions.collect
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
        setFlows()
        setListeners()
        return binding.root
    }

    private fun init() = lifecycleScope.launch {
        setAdapter()
        setRecycler()
        viewModel.getCharacter()
    }

    @SuppressLint("SetTextI18n")
    private fun setFlows() {
        collect(viewModel.state) {
            if (it.loading) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
                characterAdapter.addList(it.character)
                val count = characterAdapter.itemCount
                binding.tvNumberPeople.text = "Total $count person"
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
            layoutManager = LinearLayoutManager(requireContext())
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
                val count = characterAdapter.itemCount
                binding.tvNumberPeople.text = "Total $count person"
            }
            val count = characterAdapter.itemCount
            binding.tvNumberPeople.text = "Total $count person"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
