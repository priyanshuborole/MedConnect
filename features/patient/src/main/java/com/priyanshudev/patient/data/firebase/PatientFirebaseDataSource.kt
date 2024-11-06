package com.priyanshudev.patient.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.common.util.AppointmentStatus
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
        val collectionRef =
            firestore.collection("patients").document("MGdjwxHrlRTfI4dfQ3fVsgrMcWA3")
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


    suspend fun bookAppointment(doctorId: String, doctorName: String, startDateTime: Long): Boolean {

        val endDateTime = startDateTime + 3_600_000
        val appointment = Appointment(
            id = "Appointment$startDateTime",
            doctorId = doctorId,
            doctorName = doctorName,
            patientName = "",
            patientId = firebaseAuth.currentUser?.uid ?: "nullId",
            startDateTime = startDateTime,
            endDateTime = endDateTime
        )
        addAppointment(appointment)
        return true
//        val isAvailable = isTimeSlotAvailable(
//            doctorId,
//            startDateTime,
//            endDateTime
//        )
//        if (isAvailable) {
//            val appointment = Appointment(
//                id = "Appointment$startDateTime",
//                doctorId = doctorId,
//                patientId = firebaseAuth.currentUser?.uid ?: "nullId",
//                startDateTime = startDateTime,
//                endDateTime = endDateTime
//            )
//            addAppointment(appointment)
//            return true
//        } else {
//            return false
//        }
    }

    private suspend fun addAppointment(appointment: Appointment) {
        firestore.collection("appointments").add(appointment).await()
    }

    private suspend fun isTimeSlotAvailable(
        doctorId: String,
        startTime: Long,
        endTime: Long
    ): Boolean {
        val result = firestore.collection("appointments")
            .whereEqualTo("doctorId", doctorId)
            .whereGreaterThan("startTime", startTime)
            .whereLessThan("endTime", endTime)
            .get()
            .await()

        return result.isEmpty
    }

    suspend fun getAppointments() : MutableList<Appointment> {
        val appointments = firestore.collection("appointments")
            .whereEqualTo("patientId","MGdjwxHrlRTfI4dfQ3fVsgrMcWA3")
            .get()
            .await()
        return appointments.toObjects(Appointment::class.java)
    }

    suspend fun cancelAppointment(appointmentId: String): Boolean {
        try {
            val document = firestore.collection("appointments")
                .whereEqualTo("id",appointmentId)
                .get().await()
            firestore.collection("appointments")
                .document(document.documents[0].id)
                .delete()
                .await()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun rescheduleAppointment(appointmentId: String, startDateTime: Long): Boolean {
        try {
            val updates = mapOf(
                "startDateTime" to startDateTime,
                "status" to AppointmentStatus.PENDING.status
            )
            val document = firestore.collection("appointments")
                .whereEqualTo("id",appointmentId)
                .get().await()
            firestore.collection("appointments")
                .document(document.documents[0].id)
                .update(updates)
                .await()

            return true
        } catch (e: Exception) {
            return false
        }
    }
}