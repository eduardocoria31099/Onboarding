package com.example.onboarding.ui.fgm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.onboarding.R
import com.example.onboarding.databinding.FragmentRegisterBinding
import com.example.onboarding.viewmodel.LoginViewModel
import com.example.utils.Utils.collect
import com.example.utils.Utils.materialAlertDialog


class RegisterFragment : DialogFragment() {

    private val viewModel: LoginViewModel by activityViewModels()


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.FullScreenDialog
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setFlows()
        setListeners()
        return binding.root
    }

    private fun setFlows() {
        collect(viewModel.state) {
            if (it.message.isNotEmpty()) {
                requireContext().materialAlertDialog(title = "Message", message = it.message)
            }
            if (it.dismiss) {
                dismiss()
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                dismiss()
            }
            btnRegister.setOnClickListener {
                viewModel.validateFields(
                    name = txtName.text.toString(),
                    email = txtEmail.text.toString(),
                    password = txtPassword.text.toString(),
                    confirmPassword = txtConfirmPassword.text.toString()
                )
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetMessage()
        _binding = null
    }
}