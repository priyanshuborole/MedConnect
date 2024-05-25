package com.priyanshudev.doctor.presentation.appointment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.doctor.domain.repository.DoctorFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorAppointmentViewModel @Inject constructor(
    private val doctorFirebaseRepository: DoctorFirebaseRepository
): ViewModel() {

    private val _appointments = MutableStateFlow(emptyList<Appointment>())
    val appointments = _appointments.asStateFlow()

    init {
        getAppointments()
    }

    private fun getAppointments() = viewModelScope.launch(Dispatchers.IO) {
        val appointments = doctorFirebaseRepository.getAppointments()
        _appointments.emit(appointments)
        Log.d("PRIYANSHU", "getAppointments: $appointments")
    }

    fun updateAppointmentStatus(appointmentId: String, status: String) = viewModelScope.launch(Dispatchers.IO) {
        val isUpdated = doctorFirebaseRepository.updateAppointmentStatus(appointmentId, status)
        if (isUpdated) {
            getAppointments()
        }
    }

}