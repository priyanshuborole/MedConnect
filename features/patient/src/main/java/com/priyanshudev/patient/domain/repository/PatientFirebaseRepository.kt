package com.priyanshudev.patient.domain.repository

import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient

interface PatientFirebaseRepository {
    suspend fun getPatientsForDoctor(): MutableList<Patient>

    suspend fun getDoctorsList(): MutableList<Doctor>

}