package com.priyanshudev.common.domain.model

data class Prescription(
    val doctorName: String,
    val symptoms: String,
    val diagnose: String,
    val medicines: List<Medicines>,
    val date: String,
)

data class Medicines(
    val medicineName: String,
    val duration: Int,
    val morning: Boolean,
    val afternoon: Boolean,
    val night: Boolean,
    val dosage: String,
)
