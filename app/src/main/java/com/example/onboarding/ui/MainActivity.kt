package com.example.onboarding.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.onboarding.R
import com.example.onboarding.utils.Utils.nextActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splashScreen()
    }

    private fun splashScreen() {
        Handler().postDelayed({
            nextActivity(LoginActivity())
        }, 3000)
    }
}