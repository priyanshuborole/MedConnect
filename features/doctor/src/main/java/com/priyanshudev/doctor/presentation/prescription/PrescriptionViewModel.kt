package com.priyanshudev.doctor.presentation.prescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.doctor.domain.repository.DoctorFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(
    private val doctorFirebaseRepository: DoctorFirebaseRepository
) : ViewModel() {
    fun addPrescription(prescription: Prescription) = viewModelScope.launch(Dispatchers.IO) {
        doctorFirebaseRepository.addPrescription(prescription)
    }
}