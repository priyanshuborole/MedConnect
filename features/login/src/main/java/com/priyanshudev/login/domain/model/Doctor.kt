package com.priyanshudev.login.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    val doctorId: String,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val license: String,
    val specialization: String,
    val degree: String,
    val gender: String,
    val profileImage: String? = null,
    val clinicLogo: String? = null
) : Parcelable
