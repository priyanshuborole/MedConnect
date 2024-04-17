package com.priyanshudev.doctor.presentation.patientDetails

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
import com.priyanshudev.common.domain.model.Medicines
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.doctor.databinding.FragmentPatientDetailsBinding


class PatientDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPatientDetailsBinding
    private val phoneNumber = "8483919735"
    private var prescriptions: List<Prescription> = listOf()
    private val adapter by lazy {
        PatientDetailsAdapter(prescriptions)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPatientDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener { requireActivity().onBackPressedDispatcher }
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

    private fun initViews() {
        val medicine1 = Medicines("Paracetamol", 7, true, false, true, "500mg")
        val medicine2 = Medicines("Cetrizine", 5, false, true, false, "10mg")
        val medicine3 = Medicines("Amoxicillin", 10, true, true, true, "250mg")

        val prescription1 = Prescription("Dr. Smith", "Fever", "Common cold", listOf(medicine1), "2024-04-05")
        val prescription2 = Prescription("Dr. Johnson", "Allergy", "Seasonal allergy", listOf(medicine2), "2024-04-05")
        val prescription3 = Prescription("Dr. Brown", "Sore throat", "Strep throat", listOf(medicine3), "2024-04-06")

        prescriptions = listOf(prescription1, prescription2, prescription3)
        binding.rvPatientHistory.adapter = adapter
    }
}