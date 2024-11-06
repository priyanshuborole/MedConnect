package com.priyanshudev.patient.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.patient.domain.repository.PatientFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel@Inject constructor(
    private val patientFirebaseRepository: PatientFirebaseRepository
): ViewModel() {

    private val _appointments = MutableStateFlow(emptyList<Appointment>())
    val appointments = _appointments.asStateFlow()

    init {
        getAppointments()
    }

    fun bookAppointment(doctorId: String, doctorName: String, startDateTime:Long) = viewModelScope.launch(Dispatchers.IO){
        val isBooked = patientFirebaseRepository.bookAppointment(doctorId, doctorName, startDateTime)
        Log.d("PRIYANSHU", "bookAppointment: $isBooked")
    }

    private fun getAppointments() = viewModelScope.launch(Dispatchers.IO) {
        val appointments = patientFirebaseRepository.getAppointments()
        _appointments.emit(appointments)
        Log.d("PRIYANSHU", "getAppointments: $appointments")
    }

    fun cancelAppointment(appointmentId: String) = viewModelScope.launch(Dispatchers.IO) {
        val isCancelled = patientFirebaseRepository.cancelAppointment(appointmentId)
        if (isCancelled){
            getAppointments()
        }
        Log.d("PRIYANSHU", "cancelAppointment: $isCancelled")
    }

    fun rescheduleAppointment(appointmentId: String, startDateTime: Long) = viewModelScope.launch(Dispatchers.IO) {
        val isRescheduled = patientFirebaseRepository.rescheduleAppointment(appointmentId, startDateTime)
        if (isRescheduled){
            getAppointments()
        }
        Log.d("PRIYANSHU", "rescheduleAppointment: $isRescheduled")
    }

}