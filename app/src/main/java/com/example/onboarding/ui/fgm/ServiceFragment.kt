package com.example.onboarding.ui.fgm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.onboarding.databinding.FragmentServiceBinding

class ServiceFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.apply {
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
