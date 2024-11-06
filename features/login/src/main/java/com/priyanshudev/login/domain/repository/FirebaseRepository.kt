package com.priyanshudev.login.domain.repository

import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.login.utils.Resource

interface FirebaseRepository {
    suspend fun saveDoctorProfileData(doctor: Doctor)
    suspend fun getDoctorProfileData(doctorId: String): Resource<Doctor>
    suspend fun savePatientProfileData(patient: Patient)
    suspend fun getPatientProfileData(patientId: String): Resource<Patient>
}