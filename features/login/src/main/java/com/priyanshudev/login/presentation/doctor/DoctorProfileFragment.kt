package com.priyanshudev.login.presentation.doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.repository.MedConnectDataStore
import com.priyanshudev.doctor.DoctorActivity
import com.priyanshudev.login.R
import com.priyanshudev.login.databinding.FragmentDoctorProfileBinding
import com.priyanshudev.login.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DoctorProfileFragment : Fragment() {

    private lateinit var binding: FragmentDoctorProfileBinding

    @Inject
    lateinit var medConnectDataStore: MedConnectDataStore

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorProfileBinding.inflate(layoutInflater, container, false)
        initData()
        initListener()
        return binding.root
    }

    private fun initData() {
        val gender = listOf("Male", "Female", "Other")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, gender)
        binding.actvGender.setAdapter(arrayAdapter)
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            if(validateInput()) {
                val doctorId = firebaseAuth.currentUser?.uid ?: "nullId"
                val doctor = Doctor(
                    doctorId,
                    binding.tiName.editText?.text.toString(),
                    binding.tiNumber.editText?.text.toString(),
                    binding.tiEmail.editText?.text.toString(),
                    binding.tiAddress.editText?.text.toString(),
                    binding.tiLicense.editText?.text.toString(),
                    binding.tiSpecialization.editText?.text.toString(),
                    binding.tiDegree.editText?.text.toString(),
                    binding.tiGender.editText?.text.toString()
                )
                viewModel.saveDoctorProfileData(doctor)
                lifecycleScope.launch(Dispatchers.IO) {
                    medConnectDataStore.putBoolean("SignedIn", true)
                }
                val intent = Intent(requireActivity(), DoctorActivity::class.java)
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
        } else if (binding.etLicense.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter License Number")
            return false
        } else if (binding.etSpecialization.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter Specialization")
            return false
        } else if (binding.etDegree.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter Degree")
            return false
        } else if (binding.actvGender.text.isNullOrEmpty()) {
            displaySnackbar("Please Select Gender")
            return false
        } else if (binding.etLicense.text.isNullOrEmpty()) {
            displaySnackbar("Please Enter Name")
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