package com.priyanshudev.patient.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.priyanshudev.patient.R
import com.priyanshudev.patient.databinding.FragmentPatientHomeBinding
import com.priyanshudev.patient.presentation.home.components.DoctorListItem
import com.priyanshudev.patient.presentation.home.components.HomeScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientHomeFragment : Fragment() {

    private lateinit var binding: FragmentPatientHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientHomeBinding.inflate(layoutInflater,container,false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    HomeScreen(viewModel){
                        val action = PatientHomeFragmentDirections.actionPatientHomeFragmentToDoctorDetailFragment(it)
                        findNavController().navigate(action)
                    }
                }
            }
        }
        return binding.root
    }
}