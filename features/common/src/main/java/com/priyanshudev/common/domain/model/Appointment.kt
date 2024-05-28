package com.priyanshudev.common.domain.model

import android.os.Parcelable
import com.priyanshudev.common.util.AppointmentStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointment(
    val id: String = "",
    val doctorName: String = "",
    val patientName: String = "",
    val patientId: String = "",
    val doctorId: String = "",
    val startDateTime: Long = System.currentTimeMillis(),
    val endDateTime: Long = System.currentTimeMillis(),
    val status: String = AppointmentStatus.PENDING.status
) : Parcelable
