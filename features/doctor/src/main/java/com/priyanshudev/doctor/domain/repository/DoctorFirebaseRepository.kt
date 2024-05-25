package com.priyanshudev.doctor.domain.repository

import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription

interface DoctorFirebaseRepository {
//    suspend fun getPatientsList(): MutableList<Patient>
    suspend fun getPatientsForDoctor(): List<Patient>
    suspend fun getPrescriptionForDoctor():MutableList<Prescription>
}