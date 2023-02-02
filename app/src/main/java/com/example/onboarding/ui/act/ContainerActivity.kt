package com.example.onboarding.ui.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.onboarding.App.Companion.datastore
import com.example.onboarding.R
import com.example.onboarding.databinding.ActivityContainerBinding
import com.example.onboarding.ui.fgm.HomeFragment
import com.example.onboarding.ui.fgm.ListFragment
import com.example.onboarding.ui.fgm.ServiceFragment
import com.example.utils.ExtendedFunctions.nextActivity
import com.example.utils.ExtendedFunctions.replaceFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class ContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        showBottomNavigate()
        setListeners()
    }

    private fun init() {
        replaceFragment(ListFragment(), R.id.frameLayout)
        binding.topAppBar.apply {
            title = "List"
        }
    }

    private fun showBottomNavigate() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    replaceFragment(ListFragment(), R.id.frameLayout)
                    binding.topAppBar.apply {
                        title = "List"
                    }
                    true
                }
                R.id.page_2 -> {
                    replaceFragment(HomeFragment(), R.id.frameLayout)
                    binding.topAppBar.apply {
                        title = "Home"
                    }

                    true
                }
                R.id.page_3 -> {
                    replaceFragment(ServiceFragment(), R.id.frameLayout)
                    binding.topAppBar.apply {
                        title = "Service"
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            showTopBar()
        }
    }

    private fun showTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_logout -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        datastore.edit {
                            it[booleanPreferencesKey("vip")] = false
                        }
                    }
                    nextActivity(LoginActivity())
                    true
                }
                else -> false
            }
        }

    }
}