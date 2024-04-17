package com.priyanshudev.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.priyanshudev.doctor.databinding.ActivityDoctorBinding
import com.priyanshudev.doctor.presentation.DoctorAppointmentFragment
import com.priyanshudev.doctor.presentation.home.DoctorDashboardFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.doctorFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
                binding.bDashboard.setOnClickListener {
                    Log.d("VIBHUTI", "bDashboard")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.doctorFragmentContainer, DoctorDashboardFragment())
                        .commit()
                    true
                }

                binding.bAddPatient.setOnClickListener {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.doctorFragmentContainer, DoctorAppointmentFragment())
                        .commit()
                    true
                }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = destination.id != R.id.patientDetailsFragment
        }

//                else -> false
//            }
//        }
    }
}