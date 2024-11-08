package com.priyanshudev.patient.presentation.appointment

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.util.AppointmentStatus
import com.priyanshudev.patient.R
import com.priyanshudev.patient.presentation.appointment.component.DateTimePickerDialog
import com.priyanshudev.patient.presentation.main.viewmodel.AppointmentViewModel
import com.priyanshudev.patient.theme.strokeColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PatientAppointmentScreen(
    viewModel: AppointmentViewModel = hiltViewModel()
) {
    val appointments by viewModel.appointments.collectAsState()
    val groupedAppointments = groupAppointmentsByTime(appointments)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            groupedAppointments.forEach { (header, items) ->
                item {
                    Text(
                        text = header,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
                items(items) { appointment ->
                    if (header == "Upcoming Appointments"){
                        AppointmentCardUpcoming(viewModel,appointment)
                    }else{
                        AppointmentCardPast(appointment)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun AppointmentCardUpcoming(viewModel: AppointmentViewModel,appointment: Appointment) {
    var showDateTimeDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, strokeColor),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_doctor_male),
                contentDescription = "Profile Image",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = appointment.doctorName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Date : ${convertMillisToDate(appointment.startDateTime)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Time : ${convertMillisToTime(appointment.startDateTime)} to ${
                        convertMillisToTime(
                            appointment.endDateTime
                        )
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = appointment.status,
                    color = if (appointment.status == AppointmentStatus.CONFIRMED.status) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        border = BorderStroke(1.dp, strokeColor),
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            viewModel.cancelAppointment(appointment.id)
                        }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            showDateTimeDialog = true
                        }) {
                        Text(text = "Reschedule")
                    }

                    if (showDateTimeDialog) {
                        DateTimePickerDialog(
                            onDismissRequest = { showDateTimeDialog = false },
                            onSave = { timestamp ->
                                Log.d("PRIYANSHU", "DoctorProfile: TIMESTAMP $timestamp")
                                showDateTimeDialog = false
                                viewModel.rescheduleAppointment(appointment.id, timestamp)
                            }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun AppointmentCardPast(appointment: Appointment) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, strokeColor),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_doctor_male),
                contentDescription = "Profile Image",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = appointment.doctorName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Date : ${convertMillisToDate(appointment.startDateTime)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Time : ${convertMillisToTime(appointment.startDateTime)} to ${
                        convertMillisToTime(
                            appointment.endDateTime
                        )
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (appointment.status == AppointmentStatus.CONFIRMED.status) AppointmentStatus.CONFIRMED.status else AppointmentStatus.CANCELLED.status,
                    color = if (appointment.status == AppointmentStatus.CONFIRMED.status) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }
}

fun groupAppointmentsByTime(appointments: List<Appointment>): Map<String, List<Appointment>> {
    val currentTime = System.currentTimeMillis()
    return appointments.groupBy {
        if (it.startDateTime > currentTime) "Upcoming Appointments" else "Past Appointments"
    }.entries.sortedByDescending { it.key == "Upcoming Appointments" }.associate { it.toPair() }
}

fun convertMillisToDate(millis: Long, dateFormat: String = "yyyy-MM-dd"): String {
    val date = Date(millis)
    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
    return sdf.format(date)
}

fun convertMillisToTime(millis: Long, timeFormat: String = "HH:mm"): String {
    val date = Date(millis)
    val sdf = SimpleDateFormat(timeFormat, Locale.getDefault())
    return sdf.format(date)
}
