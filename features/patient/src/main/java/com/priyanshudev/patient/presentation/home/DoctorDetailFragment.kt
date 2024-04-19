package com.priyanshudev.patient.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.priyanshudev.patient.R
import com.priyanshudev.patient.databinding.FragmentDoctorDetailBinding
import com.priyanshudev.patient.databinding.FragmentPatientHomeBinding
import com.priyanshudev.patient.presentation.home.components.HomeScreen

class DoctorDetailFragment : Fragment() {

    private lateinit var binding: FragmentDoctorDetailBinding
    private val args: DoctorDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorDetailBinding.inflate(layoutInflater,container,false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    DoctorDetailScreen(args.doctor){}
                }
            }
        }
        return binding.root
    }
}