package com.priyanshudev.login.data.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.context.AttributeContext.Resource
import com.priyanshudev.common.domain.repository.MedConnectDataStore
import com.priyanshudev.login.domain.model.Doctor
import kotlinx.coroutines.tasks.await
import java.util.UUID
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
}