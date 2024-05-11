package com.priyanshudev.patient.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    fun getPatientsForDoctor() = viewModelScope.launch(Dispatchers.IO) {
        val patients = patientFirebaseRepository.getPatientsForDoctor()
        Log.d("PRIYANSHU", "getPatientsForDoctor: $patients")
    }
    fun getPrescriptionForPatient(doctorId: String) = viewModelScope.launch(Dispatchers.IO) {
        val prescription = patientFirebaseRepository.getPrescriptionForPatient(doctorId)
        _prescriptions.emit(prescription)
        Log.d("PRIYANSHU", "getPrescriptionForPatient: $prescription")
    }

}

val doctorList = listOf<Doctor>(
//    Doctor(
//        doctorId = "DocID1",
//        name = "Dr. John Doe",
//        phoneNumber = "1234567890",
//        email = "john.doe@example.com",
//        address = "123 Main St, Anytown, USA",
//        license = "12345",
//        specialization = "Cardiology",
//        degree = "MD",
//        gender = "Male"
//    ),
//    Doctor(
//        doctorId = "DocID2",
//        name = "Dr. Jane Smith",
//        phoneNumber = "9876543210",
//        email = "jane.smith@example.com",
//        address = "456 Oak St, Othertown, USA",
//        license = "54321",
//        specialization = "Neurology",
//        degree = "DO",
//        gender = "Female"
//    ),
//    Doctor(
//        doctorId = "DocID3",
//        name = "Dr. Michael Johnson",
//        phoneNumber = "5551234567",
//        email = "michael.johnson@example.com",
//        address = "789 Elm St, Anycity, USA",
//        license = "67890",
//        specialization = "Orthopedics",
//        degree = "MD",
//        gender = "Male"
//    ),
//    Doctor(
//        doctorId = "DocID4",
//        name = "Dr. Emily Brown",
//        phoneNumber = "1112223333",
//        email = "emily.brown@example.com",
//        address = "321 Maple St, Anothercity, USA",
//        license = "09876",
//        specialization = "Pediatrics",
//        degree = "DO",
//        gender = "Female"
//    ),
//    Doctor(
//        doctorId = "DocID5",
//        name = "Dr. David Lee",
//        phoneNumber = "4445556666",
//        email = "david.lee@example.com",
//        address = "567 Pine St, Somewhere, USA",
//        license = "54321",
//        specialization = "Dermatology",
//        degree = "MD",
//        gender = "Male"
//    ),
//    Doctor(
//        doctorId = "DocID6",
//        name = "Dr. Sarah Johnson",
//        phoneNumber = "7778889999",
//        email = "sarah.johnson@example.com",
//        address = "890 Cedar St, Nowhere, USA",
//        license = "23456",
//        specialization = "Oncology",
//        degree = "MD",
//        gender = "Female"
//    ),
//    Doctor(
//        doctorId = "DocID7",
//        name = "Dr. Kevin Wang",
//        phoneNumber = "9990001111",
//        email = "kevin.wang@example.com",
//        address = "654 Birch St, Anywhere, USA",
//        license = "34567",
//        specialization = "Urology",
//        degree = "MD",
//        gender = "Male"
//    ),
//    Doctor(
//        doctorId = "DocID8",
//        name = "Dr. Amanda White",
//        phoneNumber = "2223334444",
//        email = "amanda.white@example.com",
//        address = "789 Oak St, Everywhere, USA",
//        license = "45678",
//        specialization = "Gynecology",
//        degree = "MD",
//        gender = "Female"
//    ),
//    Doctor(
//        doctorId = "DocID9",
//        name = "Dr. Brian Taylor",
//        phoneNumber = "3334445555",
//        email = "brian.taylor@example.com",
//        address = "901 Walnut St, Nowhere, USA",
//        license = "56789",
//        specialization = "Psychiatry",
//        degree = "DO",
//        gender = "Male"
//    ),
//    Doctor(
//        doctorId = "DocID10",
//        name = "Dr. Rachel Martinez",
//        phoneNumber = "6667778888",
//        email = "rachel.martinez@example.com",
//        address = "234 Cherry St, Anytown, USA",
//        license = "67890",
//        specialization = "Ophthalmology",
//        degree = "MD",
//        gender = "Female"
//    )
)