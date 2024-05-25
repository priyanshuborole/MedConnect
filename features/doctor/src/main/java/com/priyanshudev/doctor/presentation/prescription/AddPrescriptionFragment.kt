package com.priyanshudev.doctor.presentation.prescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.databinding.FragmentAddPrescriptionBinding
import com.priyanshudev.doctor.databinding.FragmentDoctorDashboardBinding
import com.priyanshudev.doctor.presentation.patientDetails.PatientDetailsFragmentDirections
import com.priyanshudev.doctor.presentation.patientDetails.components.PatientDetailsScreen
import com.priyanshudev.doctor.presentation.prescription.components.AddPrescriptionScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPrescriptionFragment : Fragment() {
    lateinit var binding: FragmentAddPrescriptionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPrescriptionBinding.inflate(layoutInflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    AddPrescriptionScreen()
                }
            }
        }
        return binding.root
    }
}