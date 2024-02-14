package com.priyanshudev.doctor.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.priyanshudev.common.domain.model.patient.Patient
import com.priyanshudev.doctor.databinding.FragmentDoctorDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class DoctorDashboardFragment : Fragment() {
    private lateinit var binding: FragmentDoctorDashboardBinding
//    private lateinit var adapter : HomeAdapter
private var patients : List<Patient> = listOf()
    private val adapter by lazy{
        HomeAdapter(patients, object : ItemClickListener {
            override fun onItemClick(patient: Patient) {
                val action = DoctorDashboardFragmentDirections.actionDoctorDashboardFragmentToPatientDetailsFragment()
                findNavController().navigate(action)
                Toast.makeText(requireContext(), "patient clicked!!", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDoctorDashboardBinding.inflate(layoutInflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){
        binding.tvDoctorName.text = "Vibhuti Patil"
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        Log.d("VIBHUTI", "setUpRecyclerView() called")
        patients = listOf(
            Patient("John Doe", "Male", 30, "O+", "None", 1L, Date(), "profile1.jpg"),
            Patient("Jane Smith", "Female", 25, "A-", "Peanuts", 2L, Date(), "profile2.jpg"),
            Patient("Alex Johnson", "Male", 40, "B+", "None", 3L, Date(), "profile3.jpg"),
            Patient("Emily Davis", "Female", 35, "AB+", "Penicillin", 4L, Date(), "profile4.jpg"),
            Patient("Michael Wilson", "Male", 28, "O-", "None", 5L, Date(), "profile5.jpg")
        )
        binding.rvPatients.adapter = adapter
    }
}