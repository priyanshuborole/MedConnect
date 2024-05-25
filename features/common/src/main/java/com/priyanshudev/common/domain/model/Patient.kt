package com.priyanshudev.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.text.Typography.degree

@Parcelize
data class Patient(
    val patientId: String,
    val name: String,
    val number: String,
    val email: String? = null,
    val age: String,
    val address: String,
    val bloodGroup: String
) : Parcelable {
    constructor() : this("","","","","","","")

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            address,
            degree
        )
        return matchingCombinations.contains(query)
    }
}
