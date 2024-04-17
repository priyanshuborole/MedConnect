package com.priyanshudev.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Patient(
    val patientId: String? = null,
    val name: String? = null,
    val number: String? = null,
    val email: String? = null,
    val age: String? = null,
    val address: String? = null,
    val bloodGroup: String? = null
) : Parcelable {
    constructor() : this("","","","","","","")
}
