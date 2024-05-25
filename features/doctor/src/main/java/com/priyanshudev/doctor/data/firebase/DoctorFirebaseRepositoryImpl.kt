package com.priyanshudev.doctor.data.firebase

import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.doctor.domain.repository.DoctorFirebaseRepository
import javax.inject.Inject

class DoctorFirebaseRepositoryImpl  @Inject constructor(
    private val firebaseDataSource: DoctorFirebaseDataSource
) : DoctorFirebaseRepository {
//    override suspend fun getPatientsList(): MutableList<Patient> {
//        return firebaseDataSource.getPatientsList()
//    }

    override suspend fun getPatientsForDoctor(): List<Patient> {
        return firebaseDataSource.getPatientsForDoctor()
    }

    override suspend fun getPrescriptionForDoctor(): MutableList<Prescription> {
        return firebaseDataSource.getPrescriptionForDoctor()
    }

    override suspend fun getAppointments(): MutableList<Appointment> {
        return firebaseDataSource.getAppointments()
    }

    override suspend fun updateAppointmentStatus(appointmentId: String, status: String): Boolean {
        return firebaseDataSource.updateAppointmentStatus(appointmentId, status)
    }
}