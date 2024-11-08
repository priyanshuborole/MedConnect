package com.priyanshudev.doctor.presentation.patientDetails

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.databinding.FragmentPatientDetailsBinding
import com.priyanshudev.doctor.presentation.home.DoctorViewModel
import com.priyanshudev.doctor.presentation.patientDetails.components.PatientDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPatientDetailsBinding
    private val args: PatientDetailsFragmentArgs by navArgs()
    private val viewModel: DoctorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPatientDetailsBinding.inflate(layoutInflater,container,false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    PatientDetailsScreen(args.patient, viewModel) {
                        val action = PatientDetailsFragmentDirections.actionPatientDetailsFragmentToAddPrescriptionFragment(args.patient)
                        findNavController().navigate(action)
                    }
                }
            }
        }
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}