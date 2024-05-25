package com.priyanshudev.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.priyanshudev.doctor.databinding.ActivityDoctorBinding
import com.priyanshudev.doctor.presentation.DoctorAppointmentFragment
import com.priyanshudev.doctor.presentation.home.DoctorDashboardFragment
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