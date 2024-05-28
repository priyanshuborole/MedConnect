package com.priyanshudev.doctor.presentation.prescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.navArgs
import com.priyanshudev.doctor.databinding.FragmentAddPrescriptionBinding
import com.priyanshudev.doctor.presentation.prescription.components.AddPrescriptionScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPrescriptionFragment : Fragment() {
    lateinit var binding: FragmentAddPrescriptionBinding
    private val args: AddPrescriptionFragmentArgs by navArgs()
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
                    AddPrescriptionScreen(args.patient)
                }
            }
        }
        return binding.root
    }
}