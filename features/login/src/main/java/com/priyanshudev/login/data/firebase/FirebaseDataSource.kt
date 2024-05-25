package com.priyanshudev.login.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.repository.MedConnectDataStore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val medConnectDataStore: MedConnectDataStore
) {
    suspend fun saveDoctorProfileData(doctor: Doctor){
        val uid = firebaseAuth.currentUser?.uid ?: "nullId"
        try {
            firebaseFirestore.collection("doctors").document(uid).set(doctor).await()
        }
        catch (e : Exception){
            Log.d("MEDCONNECT", "addDoctorToFirestore Failed: ${e.message.toString()} ")
        }
    }

    suspend fun savePatientProfileData(patient: Patient) {
        val uid = firebaseAuth.currentUser?.uid ?: "nullId"
        try {
            firebaseFirestore.collection("patients").document(uid).set(patient).await()
        }
        catch (e : Exception){
            Log.d("MEDCONNECT", "addDoctorToFirestore Failed: ${e.message.toString()} ")
        }
    }
}