package com.priyanshudev.doctor.domain.repository

import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription

interface DoctorFirebaseRepository {
    //    suspend fun getPatientsList(): MutableList<Patient>
    suspend fun getPatientsForDoctor(): List<Patient>
    suspend fun getPrescriptionForDoctor(): MutableList<Prescription>
    suspend fun getAppointments(): MutableList<Appointment>
    suspend fun updateAppointmentStatus(appointmentId: String, status: String): Boolean
    suspend fun addPrescription(prescription: Prescription): Boolean
}