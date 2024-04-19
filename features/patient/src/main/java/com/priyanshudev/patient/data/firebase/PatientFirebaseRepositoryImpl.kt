package com.priyanshudev.patient.data.firebase

import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.patient.domain.repository.PatientFirebaseRepository
import javax.inject.Inject

class PatientFirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: PatientFirebaseDataSource
) : PatientFirebaseRepository {
    override suspend fun getPatientsForDoctor(): MutableList<Patient> {
        return firebaseDataSource.getPatientsForDoctor()
    }

    override suspend fun getDoctorsList(): MutableList<Doctor> {
        return firebaseDataSource.getDoctorsList()
    }
}