package com.priyanshudev.patient.domain.repository

import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription

interface PatientFirebaseRepository {
    suspend fun getPatientsForDoctor(): MutableList<Patient>

    suspend fun getDoctorsList(): MutableList<Doctor>

    suspend fun getPrescriptionForPatient(doctorId: String): MutableList<Prescription>
}