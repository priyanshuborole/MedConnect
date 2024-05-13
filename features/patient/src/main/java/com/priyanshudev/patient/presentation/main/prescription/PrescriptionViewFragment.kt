package com.priyanshudev.patient.presentation.main.prescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.priyanshudev.patient.databinding.FragmentPrescriptionViewBinding
import com.priyanshudev.patient.presentation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PrescriptionViewFragment : Fragment() {

    private lateinit var binding: FragmentPrescriptionViewBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrescriptionViewBinding.inflate(layoutInflater,container,false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    PrescriptionViewScreen(viewModel = viewModel)
                }
            }
        }
        return binding.root
    }
}