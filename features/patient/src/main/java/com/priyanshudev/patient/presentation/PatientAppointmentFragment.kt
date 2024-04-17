package com.priyanshudev.patient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.priyanshudev.patient.R
import com.priyanshudev.patient.databinding.FragmentPatientAppointmentBinding

class PatientAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentPatientAppointmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientAppointmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}