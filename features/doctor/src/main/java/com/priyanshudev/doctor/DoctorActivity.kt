package com.priyanshudev.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.priyanshudev.doctor.databinding.ActivityDoctorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding =  ActivityDoctorBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.patientFragmentContainer) as NavHostFragment

            val navController = navHostFragment.navController

            binding.bottomNavigationView.setupWithNavController(navController)
        }
}