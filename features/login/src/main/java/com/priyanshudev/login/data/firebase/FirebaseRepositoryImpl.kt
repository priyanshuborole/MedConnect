package com.priyanshudev.login.data.firebase

import com.priyanshudev.login.domain.model.Doctor
import com.priyanshudev.login.domain.repository.FirebaseRepository
import com.priyanshudev.login.utils.Resource
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
): FirebaseRepository {
    override suspend fun saveDoctorProfileData(doctor: Doctor) {
        return firebaseDataSource.saveDoctorProfileData(doctor)
    }

    override suspend fun getDoctorProfileData(doctorId: String): Resource<Doctor> {
        TODO("Not yet implemented")
    }
}