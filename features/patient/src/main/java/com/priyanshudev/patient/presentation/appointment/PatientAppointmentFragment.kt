package com.priyanshudev.patient.presentation.appointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.priyanshudev.patient.R
import com.priyanshudev.patient.databinding.FragmentPatientAppointmentBinding
import com.priyanshudev.patient.presentation.main.prescription.PrescriptionViewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentPatientAppointmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientAppointmentBinding.inflate(layoutInflater,container,false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    PatientAppointmentScreen()
                }
            }
        }
        return binding.root
    }

}