package com.priyanshudev.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Prescription(
    val doctorName: String? = null,
    val symptoms: String? = null,
    val diagnose: String? = null,
    val observation: String? = null,
    val medicines: List<Medicines> = emptyList(),
    val date: String? = null,
    val doctorId: String? = null,
    val patientId: String? = null
) : Parcelable

@Parcelize
data class Medicines(
    val medicineName: String? = null,
    val duration: Int? = null,
    val morning: Boolean? = null,
    val afternoon: Boolean? = null,
    val night: Boolean? = null,
    val morningDosage: String? = null,
    val afternoonDosage: String? = null,
    val nightDosage: String? = null,
) : Parcelable
