package com.priyanshudev.doctor.presentation.appointment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.databinding.FragmentDoctorAppointmentBinding
import com.priyanshudev.doctor.presentation.home.components.HomeScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class  DoctorAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentDoctorAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorAppointmentBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    DoctorAppointmentScreen()
                }
            }
        }
        return binding.root
    }
}