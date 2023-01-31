package com.example.onboarding.ui.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onboarding.ui.fgm.RegisterFragment
import com.example.onboarding.databinding.ActivityLoginBinding
import com.example.utils.Utils.nextActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var registerFragment: RegisterFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setListeners()
    }

    private fun init() {
        registerFragment = RegisterFragment()
    }

    private fun setListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                nextActivity(ContainerActivity())
            }
            btnRegister.setOnClickListener {
                registerFragment.show(supportFragmentManager, "TAG")
            }
        }
    }
}