package com.priyanshudev.doctor.presentation.home

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.databinding.FragmentDoctorDashboardBinding
import com.priyanshudev.doctor.presentation.home.DoctorDashboardFragmentDirections
import com.priyanshudev.doctor.presentation.home.components.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorDashboardFragment : Fragment() {
    private lateinit var binding: FragmentDoctorDashboardBinding
    private var patients : List<Patient> = listOf()
    private val viewModel: DoctorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDoctorDashboardBinding.inflate(layoutInflater,container,false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    HomeScreen(viewModel){
                        Log.d("VIBHUTI","patient passed is $it")
                        val action = DoctorDashboardFragmentDirections.actionDoctorDashboardFragmentToPatientDetailsFragment(it)
                        findNavController().navigate(action)
                    }
                }
            }
        }
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
        return binding.root
    }
}