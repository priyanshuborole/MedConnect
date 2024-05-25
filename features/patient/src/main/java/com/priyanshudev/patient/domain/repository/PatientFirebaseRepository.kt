package com.priyanshudev.patient.domain.repository

import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription

interface PatientFirebaseRepository {
    suspend fun getPatientsForDoctor(): MutableList<Patient>

    suspend fun getDoctorsList(): MutableList<Doctor>

    suspend fun getPrescriptionForPatient(doctorId: String): MutableList<Prescription>

    suspend fun bookAppointment(doctorId: String, doctorName: String, startDateTime:Long): Boolean

    suspend fun getAppointments(): MutableList<Appointment>

    suspend fun cancelAppointment(appointmentId: String): Boolean

    suspend fun rescheduleAppointment(appointmentId: String, startDateTime: Long): Boolean
}