package com.priyanshudev.patient.presentation.main.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.priyanshudev.patient.databinding.FragmentDoctorDetailBinding
import com.priyanshudev.patient.presentation.appointment.component.DateTimePickerDialog
import com.priyanshudev.patient.presentation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorDetailFragment : Fragment() {

    private lateinit var binding: FragmentDoctorDetailBinding
    private val viewModel: HomeViewModel by viewModels()
    private val args: DoctorDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorDetailBinding.inflate(layoutInflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    DoctorDetailScreen(
                        doctor = args.doctor,
                        onItemClick = { prescription ->
                            Log.d("PRIYANSHU", "doctor passed is $prescription")
                            val action =
                                DoctorDetailFragmentDirections.actionDoctorDetailFragmentToPrescriptionViewFragment(
                                    prescription
                                )
                            findNavController().navigate(action)
                        }
                    ) { doctor ->
//                        val action =
//                            DoctorDetailFragmentDirections.actionDoctorDetailFragmentToAppointmentBookingFragment(
//                                doctor
//                            )
//                        findNavController().navigate(action)
                    }
                }
            }
        }
        return binding.root
    }
}