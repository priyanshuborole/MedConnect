package com.priyanshudev.login.presentation.patient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.repository.MedConnectDataStore
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
            if(validateInput()){
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
                requireActivity().finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        if (binding.etName.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter Name")
            return false }
        else if (binding.etNumber.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter a Phone number")
            return false
        }
        if (binding.etEmail.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter a Email Id to proceed")
            return false
        }  else if (binding.etAddress.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter Address")
            return false
        } else if (binding.etAge.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter License Number")
            return false
        } else if (binding.etBloodGroup.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter Specialization")
            return false
        } else if (binding.etAge.text.toString().toInt()==0) {
            displaySnackbar("Please Enter Degree")
            return false
        } else if (binding.etNumber.length() != 10) {
            displaySnackbar("Please Enter a Valid Phone Number")
            return false
        } else if(!validateEmail(binding.etEmail.text.toString())){
            displaySnackbar("Please Enter a Valid Email Id")
            return false
        }
        return true
    }

    private fun validateEmail(email: String): Boolean { return "^[A-Za-z](.*)([@])(.+)(\\.)(.+)".toRegex().matches(email) }

    private fun displaySnackbar(toastMessage: String) {
        Snackbar.make(binding.clMain, toastMessage, Snackbar.LENGTH_SHORT).show()
    }
}