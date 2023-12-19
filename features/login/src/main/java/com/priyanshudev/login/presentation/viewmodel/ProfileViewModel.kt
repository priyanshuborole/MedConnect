package com.priyanshudev.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshudev.login.domain.model.Doctor
import com.priyanshudev.login.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {
    fun saveDoctorProfileData(doctor: Doctor) = viewModelScope.launch(Dispatchers.IO){
        firebaseRepository.saveDoctorProfileData(doctor)
    }

}