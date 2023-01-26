package com.example.onboarding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onboarding.HomeFragment
import com.example.onboarding.ListFragment
import com.example.onboarding.R
import com.example.onboarding.ServiceFragment
import com.example.onboarding.databinding.ActivityContainerBinding
import com.example.onboarding.utils.Utils.replaceFragment

class ContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment(), R.id.frameLayout)
        showBottomNavigate()
    }

    private fun showBottomNavigate() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    replaceFragment(ListFragment(), R.id.frameLayout)
                    true
                }
                R.id.page_2 -> {
                    replaceFragment(HomeFragment(), R.id.frameLayout)
                    true
                }
                R.id.page_3 -> {
                    replaceFragment(ServiceFragment(), R.id.frameLayout)
                    true
                }
                else -> {
                    replaceFragment(HomeFragment(), R.id.frameLayout)
                    true
                }
            }
        }
    }
}