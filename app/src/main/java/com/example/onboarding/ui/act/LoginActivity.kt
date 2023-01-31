package com.example.onboarding.ui.act

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.onboarding.databinding.ActivityLoginBinding
import com.example.onboarding.ui.fgm.RegisterFragment
import com.example.onboarding.viewmodel.LoginViewModel
import com.example.utils.Utils.collect
import com.example.utils.Utils.materialAlertDialog
import com.example.utils.Utils.nextActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var registerFragment: RegisterFragment
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setFlows()
        setListeners()
    }

    private fun init() {
        registerFragment = RegisterFragment()
    }

    private fun setFlows() {
        collect(viewModel.state) {
            if (it.message.isNotEmpty()) {
                materialAlertDialog(title = "Message", message = it.message)
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
                registerFragment.show(supportFragmentManager, "TAG")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetValues()
    }
}