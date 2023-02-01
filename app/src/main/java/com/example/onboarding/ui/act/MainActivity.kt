package com.example.onboarding.ui.act

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.onboarding.App.Companion.datastore
import com.example.onboarding.R
import com.example.utils.Utils.nextActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splashScreen()

    }

    private fun splashScreen() {
        Handler().postDelayed({
            lifecycleScope.launch(Dispatchers.Main) {
                getValues().collect {
                    if (it) {
                        nextActivity(ContainerActivity())
                    } else if (!it) {
                        nextActivity(LoginActivity())
                    }
                }
            }
        }, 3000)
    }

    private fun getValues() = datastore.data.map {
        it[booleanPreferencesKey("vip")] ?: false
    }
}