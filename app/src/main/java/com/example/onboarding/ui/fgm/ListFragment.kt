package com.example.onboarding.ui.fgm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onboarding.databinding.FragmentListBinding
import com.example.onboarding.ui.adapter.PersonAdapter
import com.example.onboarding.viewmodel.ContainerViewModel
import com.example.utils.ExtendedFunctions.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var personAdapter: PersonAdapter
    private val viewModel: ContainerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        init()
        setFlows()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.apply {
            tvNumberPeople.text = personAdapter.itemCount.toString()
        }
    }

    private fun setFlows() = lifecycleScope.launch {
        collect(viewModel.inventory) {
            personAdapter.addAll(it)
            validateCont()
        }
    }

    private fun validateCont() = with(binding) {
        val count = personAdapter.itemCount
        tvNumberPeople.text = count.toString()
        if (count == 0) {
            emptyState.root.visibility = View.VISIBLE
        } else {
            emptyState.root.visibility = View.GONE
        }
    }

    private fun init() {
        setAdapter()
        setRecycler()
    }

    private fun setAdapter() {
        personAdapter = PersonAdapter(emptyList()) {

        }
    }

    private fun setRecycler() = with(binding) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = personAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}