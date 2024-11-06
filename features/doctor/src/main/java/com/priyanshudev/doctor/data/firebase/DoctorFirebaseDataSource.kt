package com.priyanshudev.doctor.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DoctorFirebaseDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun getPatientsList(): MutableList<Patient> {
        val collectionRef = firestore.collection("patients")
        val doctorsList = mutableListOf<Patient>()
        val snapshot = collectionRef.get().await()
        Log.d("VIBHUTI", "getDoctorsList: snapshots - $snapshot")
        for (document in snapshot!!.documents) {
            val doctor = document.toObject(Patient::class.java)
            doctor?.let {
                doctorsList.add(it)
            }
            Log.d("Fire6store", document.id + " => " + document.data)
        }
        return doctorsList
    }

    suspend fun getPatientsForDoctor(): MutableList<Patient> {
//        val uid = firebaseAuth.currentUser?.uid ?: "nullId"
        val uid = "MGdjwxHrlRTfI4dfQ3fVsgrMcWA3"
        val collectionRef = firestore.collection("doctors").document(uid)
            .collection("patients")
        val patients = mutableListOf<Patient>()
        val snapshot = collectionRef.get().await()
        val patientIds = mutableListOf<String>()
        for (document in snapshot!!.documents) {
            val patientId = document.getString("patientId")
            patientId?.let {
                patientIds.add(it)
                Log.d("Fire6store", "${document.id} => $it")
            }
        }

        if (patientIds.isNotEmpty()) {
            val collectionRefPatient = firestore.collection("patients")
            patientIds.forEach { patientId ->
                val snapshotPatient =
                    collectionRefPatient.whereEqualTo("patientId", patientId.trim()).get().await()
                for (document in snapshotPatient!!.documents) {
                    val patient = document.toObject(Patient::class.java)
                    patient?.let {
                        patients.add(it)
                    }
                    Log.d("Fire6store", document.id + " => " + document.data)
                }
            }
        }
        return patients
    }


    suspend fun getPrescriptionForDoctor(): MutableList<Prescription> {
//        val doctorId = firebaseAuth.currentUser?.uid ?: "nullId"
        val doctorId = "xQxwws9ta6ZcOpVbptxTy0qVldx2"
//        val uid = firebaseAuth.currentUser?.uid ?: "nullId"
        val uid = "MGdjwxHrlRTfI4dfQ3fVsgrMcWA3"
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
            Log.d("Prescription", document.id + " => " + document.data)
        }
        return prescriptions
    }

    suspend fun getAppointments(): MutableList<Appointment> {
        val appointments = firestore.collection("appointments")
            .whereEqualTo("doctorId", "xQxwws9ta6ZcOpVbptxTy0qVldx2")
            .get()
            .await()
        return appointments.toObjects(Appointment::class.java)
    }

    suspend fun updateAppointmentStatus(appointmentId: String, status: String): Boolean {
        try {
            val document = firestore.collection("appointments")
                .whereEqualTo("id", appointmentId)
                .get().await()

            firestore.collection("appointments")
                .document(document.documents[0].id)
                .update("status", status)
                .await()

            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun addPrescription(prescription: Prescription): Boolean {
        try {
            prescription.patientId?.let {
                firestore.collection("patients")
                    .document(it)
                    .collection("prescriptions")
                    .add(prescription)
                    .await()
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

}