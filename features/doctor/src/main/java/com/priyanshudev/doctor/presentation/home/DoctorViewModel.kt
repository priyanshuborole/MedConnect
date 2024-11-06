package com.priyanshudev.doctor.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.doctor.domain.repository.DoctorFirebaseRepository
import dagger.hilt.android.AndroidEntryPoint
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
class DoctorViewModel @Inject constructor(
    private val doctorFirebaseRepository: DoctorFirebaseRepository
): ViewModel(){
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _prescriptions = MutableStateFlow(emptyList<Prescription>())
    private val _patients = MutableStateFlow(emptyList<Patient>())
    val prescriptions = _prescriptions.asStateFlow()
    @OptIn(FlowPreview::class)
    val patient = searchText
        .debounce(250L)
        .combine(_patients){ text, patients ->
            if (text.isBlank()){
                patients
            }
            else {
                patients.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _patients.value
        )

    init {
        getPatientsList()
    }

    private fun getPatientsList() = viewModelScope.launch(Dispatchers.IO) {
        val patientsList = doctorFirebaseRepository.getPatientsForDoctor()
        _patients.emit(patientsList)
        Log.d("VIBHUTIII", "getPatientList: $patientsList")
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getPrescription()= viewModelScope.launch(Dispatchers.IO) {
        val prescriptionList = doctorFirebaseRepository.getPrescriptionForDoctor()
        _prescriptions.emit(prescriptionList)
    }
}