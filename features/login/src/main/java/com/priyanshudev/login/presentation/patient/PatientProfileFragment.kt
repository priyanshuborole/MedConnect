package com.priyanshudev.login.presentation.patient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.repository.MedConnectDataStore
import com.priyanshudev.doctor.DoctorActivity
import com.priyanshudev.login.databinding.FragmentPatientProfileBinding
import com.priyanshudev.login.presentation.viewmodel.ProfileViewModel
import com.priyanshudev.patient.PatientActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PatientProfileFragment : Fragment() {

    private lateinit var binding: FragmentPatientProfileBinding

    @Inject
    lateinit var medConnectDataStore: MedConnectDataStore

    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {

            if (
                binding.tiName.editText?.text.isNullOrBlank() ||
                binding.tiNumber.editText?.text.isNullOrBlank() ||
                binding.tiEmail.editText?.text.isNullOrBlank() ||
                binding.tiAddress.editText?.text.isNullOrBlank() ||
                binding.tiAge.editText?.text.isNullOrBlank() ||
                binding.tiBloodGroup.editText?.text.isNullOrBlank()
            ) {
                Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_SHORT).show()

            } else {
                val patient = Patient(
                    "doctorId",
                    binding.tiName.editText?.text.toString(),
                    binding.tiNumber.editText?.text.toString(),
                    binding.tiEmail.editText?.text.toString(),
                    binding.tiAddress.editText?.text.toString(),
                    binding.tiAge.editText?.text.toString(),
                    binding.tiBloodGroup.editText?.text.toString()
                )
                viewModel.savePatientProfileData(patient)

                lifecycleScope.launch(Dispatchers.IO) {
                    medConnectDataStore.putBoolean("PatientSignedIn", true)
                }

                val intent = Intent(requireActivity(), PatientActivity::class.java)
                startActivity(intent)
            }
        }
    }
}