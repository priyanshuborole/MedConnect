package com.priyanshudev.login.domain.repository

import com.priyanshudev.login.domain.model.Doctor
import com.priyanshudev.login.utils.Resource

interface FirebaseRepository {
    suspend fun saveDoctorProfileData(doctor: Doctor)
    suspend fun getDoctorProfileData(doctorId: String): Resource<Doctor>
}