package com.priyanshudev.common.domain.model

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
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "", "", "", null, null)
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            address,
            specialization,
            degree
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}