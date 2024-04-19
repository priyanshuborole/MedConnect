package com.priyanshudev.login.presentation.doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.repository.MedConnectDataStore
import com.priyanshudev.doctor.DoctorActivity
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener(){
        binding.btnSave.setOnClickListener {
            if (
                binding.tiName.editText?.text.isNullOrBlank() ||
                binding.tiNumber.editText?.text.isNullOrBlank() ||
                binding.tiEmail.editText?.text.isNullOrBlank() ||
                binding.tiAddress.editText?.text.isNullOrBlank() ||
                binding.tiLicense.editText?.text.isNullOrBlank() ||
                binding.tiSpecialization.editText?.text.isNullOrBlank() ||
                binding.tiDegree.editText?.text.isNullOrBlank()
            ) {
                Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_SHORT).show()

            } else {
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
            }

        }
    }
}