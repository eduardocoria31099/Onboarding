package com.example.onboarding.ui.act

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.onboarding.App.Companion.datastore
import com.example.onboarding.R
import com.example.onboarding.databinding.ActivityLoginBinding
import com.example.onboarding.ui.fgm.RegisterFragment
import com.example.onboarding.viewmodel.LoginViewModel
import com.example.onboarding.viewmodel.LoginViewModelFactory
import com.example.utils.Constants.REGISTER_FRAGMENT
import com.example.utils.ExtendedFunctions.collect
import com.example.utils.ExtendedFunctions.materialAlertDialog
import com.example.utils.ExtendedFunctions.nextActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var registerFragment: RegisterFragment

    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory(datastore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        registerFragment = RegisterFragment()
        setFlows()
        setListeners()
    }

    private fun setFlows() {
        collect(viewModel.state) {
            if (it.message.isNotEmpty()) {
                materialAlertDialog(title = getString(R.string.message), message = it.message)
            }
            if (it.nextActivity) {
                nextActivity(ContainerActivity())
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.validateFieldsLogin(
                    email = txtEmail.text.toString(),
                    password = txtPassword.text.toString(),
                )
            }
            btnRegister.setOnClickListener {
                cleanView()
                registerFragment.show(supportFragmentManager, REGISTER_FRAGMENT)
            }
        }
    }

    private fun cleanView() = with(binding) {
        txtEmail.setText("")
        txtPassword.setText("")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetValues()
    }
}