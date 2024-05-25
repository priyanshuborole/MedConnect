package com.priyanshudev.patient.data.firebase

import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
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

    override suspend fun getPrescriptionForPatient(doctorId: String): MutableList<Prescription> {
        return firebaseDataSource.getPrescriptionForPatient(doctorId)
    }

    override suspend fun bookAppointment(doctorId: String, startDateTime:Long) : Boolean {
        return firebaseDataSource.bookAppointment(doctorId, startDateTime)
    }

    override suspend fun getAppointments(): MutableList<Appointment> {
        return firebaseDataSource.getAppointments()
    }
}