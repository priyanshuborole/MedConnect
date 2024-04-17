package com.priyanshudev.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.priyanshudev.patient.databinding.ActivityPatientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPatientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityPatientBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_patient)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.patientFragmentContainer) as NavHostFragment

        //Each NavHostFragment has a NavController that defines valid navigation within the navigationhost.
        val navController = navHostFragment.navController


//        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}