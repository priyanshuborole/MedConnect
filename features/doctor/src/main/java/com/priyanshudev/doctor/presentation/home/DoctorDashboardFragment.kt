package com.priyanshudev.doctor.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.doctor.databinding.FragmentDoctorDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

@AndroidEntryPoint
class DoctorDashboardFragment : Fragment() {
    private lateinit var binding: FragmentDoctorDashboardBinding
    private val firestore = Firebase.firestore
    private val firebaseAuth = Firebase.auth
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViews()
    }

    private fun initData(): MutableList<Doctor> {
        val uid = firebaseAuth.currentUser!!.uid
        val collectionRef = firestore.collection("doctors").document(uid).collection("patients").document()
        val patientList = mutableListOf<Doctor>()
        val snapshot = this.lifecycleScope.launch {
            collectionRef.get().await()
        }

        Log.d("PRIYANSHU", "getDoctorsList: snapshots - $snapshot")
        for (document in snapshot.children) {
            val doctor = document.toObject(Doctor::class.java)
            doctor?.let {
                doctorsList.add(it)
            }
            Log.d("Fire6store", document.id + " => " + document.data)
        }
        return doctorsList
    }

    private fun initViews(){
        binding.tvDoctorName.text = "Vibhuti Patil"
        setUpRecyclerView()
    }

//    private fun setUpRecyclerView(){
//        patients = listOf(
//            Patient("John Doe", "Male", 30, "O+", "None", 1L, Date(), "profile1.jpg"),
//            Patient("Jane Smith", "Female", 25, "A-", "Peanuts", 2L, Date(), "profile2.jpg"),
//            Patient("Alex Johnson", "Male", 40, "B+", "None", 3L, Date(), "profile3.jpg"),
//            Patient("Emily Davis", "Female", 35, "AB+", "Penicillin", 4L, Date(), "profile4.jpg"),
//            Patient("Michael Wilson", "Male", 28, "O-", "None", 5L, Date(), "profile5.jpg")
//        )
//        binding.rvPatients.adapter = adapter
//    }

}