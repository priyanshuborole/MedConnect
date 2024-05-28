package com.priyanshudev.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.priyanshudev.login.R
import com.priyanshudev.login.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.tvManagePatients.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_doctorLoginFragment)
            requireActivity().finish()
        }
        binding.tvExploreDoctors.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_patientLoginFragment)
            requireActivity().finish()
        }
        return binding.root
    }
}