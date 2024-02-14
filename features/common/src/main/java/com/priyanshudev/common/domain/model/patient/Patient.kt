package com.priyanshudev.common.domain.model.patient

import java.util.Date

data class Patient(
    val name: String,
    val gender: String,
    val age: Int,
    val bloodGrp: String,
    val allergy: String,
    val id: Long,
    val date: Date,
    val profilePic: String
)
