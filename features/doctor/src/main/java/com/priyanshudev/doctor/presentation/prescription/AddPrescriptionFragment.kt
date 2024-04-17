package com.priyanshudev.doctor.presentation.prescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.databinding.FragmentAddPrescriptionBinding
import com.priyanshudev.doctor.databinding.FragmentDoctorDashboardBinding
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
        return binding.root
    }
}