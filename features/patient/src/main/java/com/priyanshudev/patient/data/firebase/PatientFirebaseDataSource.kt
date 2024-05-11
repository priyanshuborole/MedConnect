package com.priyanshudev.patient.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PatientFirebaseDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun getDoctorsList(): MutableList<Doctor> {
        val collectionRef = firestore.collection("doctors")
        val doctorsList = mutableListOf<Doctor>()
        val snapshot = collectionRef.get().await()
        Log.d("PRIYANSHU", "getDoctorsList: snapshots - $snapshot")
        for (document in snapshot!!.documents) {
            val doctor = document.toObject(Doctor::class.java)
            doctor?.let {
                doctorsList.add(it)
            }
            Log.d("Fire6store", document.id + " => " + document.data)
        }
        return doctorsList
    }

    suspend fun getPatientsForDoctor(): MutableList<Patient> {
        val uid = firebaseAuth.currentUser?.uid ?: "nullId"
        val collectionRef = firestore.collection("doctors").document(uid)
            .collection("patients")
        val patients = mutableListOf<Patient>()
        val snapshot = collectionRef.get().await()
        Log.d("PRIYANSHU", "getPatientsForDoctor: snapshots - $snapshot")
        for (document in snapshot!!.documents) {
            val patient = document.toObject(Patient::class.java)
            patient?.let {
                patients.add(it)
            }
            Log.d("Fire6store", document.id + " => " + document.data)
        }
        return patients
    }

    suspend fun getPrescriptionForPatient(doctorId: String): MutableList<Prescription> {
        val uid = firebaseAuth.currentUser?.uid ?: "nullId"
        val collectionRef = firestore.collection("patients").document(uid)
            .collection("prescriptions")

        val prescriptions = mutableListOf<Prescription>()
        val snapshot = collectionRef.whereEqualTo("doctorId", doctorId).get().await()
        Log.d("PRIYANSHU", "getPrescriptionForPatient: snapshots - $snapshot")
        for (document in snapshot!!.documents) {
            val prescription = document.toObject(Prescription::class.java)
            prescription?.let {
                prescriptions.add(it)
            }
            Log.d("Fire6store", document.id + " => " + document.data)
        }
        return prescriptions
    }
}