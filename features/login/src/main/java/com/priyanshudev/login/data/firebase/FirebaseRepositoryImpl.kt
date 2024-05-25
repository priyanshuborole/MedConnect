package com.priyanshudev.login.data.firebase

import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.login.domain.repository.FirebaseRepository
import com.priyanshudev.login.utils.Resource
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
): FirebaseRepository {
    override suspend fun saveDoctorProfileData(doctor: Doctor) {
        firebaseDataSource.saveDoctorProfileData(doctor)
    }

    override suspend fun getDoctorProfileData(doctorId: String): Resource<Doctor> {
        TODO("Not yet implemented")
    }

    override suspend fun savePatientProfileData(patient: Patient) {
        firebaseDataSource.savePatientProfileData(patient)
    }

    override suspend fun getPatientProfileData(patientId: String): Resource<Patient> {
        TODO("Not yet implemented")
    }
}