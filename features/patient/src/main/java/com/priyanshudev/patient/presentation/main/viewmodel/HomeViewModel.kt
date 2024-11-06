package com.priyanshudev.patient.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.patient.domain.repository.PatientFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val patientFirebaseRepository: PatientFirebaseRepository
): ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _prescriptions = MutableStateFlow(emptyList<Prescription>())
    val prescriptions = _prescriptions.asStateFlow()


    private val _doctors = MutableStateFlow(emptyList<Doctor>())
    @OptIn(FlowPreview::class)
    val doctors = searchText
        .debounce(250L)
        .combine(_doctors){ text, doctors ->
            if (text.isBlank()){
                doctors
            } else {
                doctors.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _doctors.value
        )


    init {
        getDoctorsList()
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getDoctorsList() = viewModelScope.launch(Dispatchers.IO) {
        val doctorsList = patientFirebaseRepository.getDoctorsList()
        _doctors.emit(doctorsList)
        Log.d("PRIYANSHU", "getDoctorsList: $doctorsList")
    }
    fun getPrescriptionForPatient(doctorId: String) = viewModelScope.launch(Dispatchers.IO) {
        val prescription = patientFirebaseRepository.getPrescriptionForPatient(doctorId)
        _prescriptions.emit(prescription)
        Log.d("PRIYANSHU", "getPrescriptionForPatient: $prescription")
    }
}