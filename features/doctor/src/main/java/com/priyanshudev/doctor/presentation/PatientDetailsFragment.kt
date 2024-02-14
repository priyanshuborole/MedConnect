package com.priyanshudev.doctor.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.priyanshudev.doctor.databinding.FragmentPatientDetailsBinding


class PatientDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPatientDetailsBinding
    private val phoneNumber = "8483919735"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPatientDetailsBinding.inflate(layoutInflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.btnAddPrescription.setOnClickListener {
            val action = PatientDetailsFragmentDirections.actionPatientDetailsFragmentToAddPrescriptionFragment()
            findNavController().navigate(action)
        }
        binding.bCall.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                requireContext().startActivity(callIntent)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf<String>(Manifest.permission.CALL_PHONE),
                    1234
                )
            }
        }
    }
}