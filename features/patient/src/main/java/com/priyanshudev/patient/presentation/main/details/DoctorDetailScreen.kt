package com.priyanshudev.patient.presentation.main.details

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyanshudev.common.domain.model.Appointment
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.patient.R
import com.priyanshudev.patient.presentation.appointment.component.DateTimePickerDialog
import com.priyanshudev.patient.presentation.main.viewmodel.AppointmentViewModel
import com.priyanshudev.patient.presentation.main.viewmodel.HomeViewModel
import com.priyanshudev.patient.theme.headerColor
import com.priyanshudev.patient.theme.strokeColor

@Composable
fun DoctorDetailScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel(),
    doctor: Doctor,
    onItemClick: (Prescription) -> Unit,
    onButtonClick: (Doctor) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getPrescriptionForPatient(doctor.doctorId)
    }
    val prescriptions by viewModel.prescriptions.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DoctorProfile(appointmentViewModel,doctor, onButtonClick)
        Spacer(modifier = Modifier.height(16.dp))
        PastHistory(prescriptions = prescriptions, onItemClick)
    }
}

@Composable
fun DoctorProfile(appointmentViewModel: AppointmentViewModel,doctor: Doctor, onButtonClick: (Doctor) -> Unit) {
    var showDateTimeDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0, 0, 15, 15),
        colors = CardDefaults.cardColors(
            containerColor = headerColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_doctor_male),
                contentDescription = "Profile Image",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Degree - ${doctor.degree}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Specialization - ${doctor.specialization}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "License No. - ${doctor.license}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Gender - ${doctor.gender}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Phone Number - ${doctor.phoneNumber}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        showDateTimeDialog = true
                    },
                    Modifier.wrapContentSize(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_appointment),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Book Appointment", style = MaterialTheme.typography.bodyMedium)
                }

                if (showDateTimeDialog) {
                    DateTimePickerDialog(
                        onDismissRequest = { showDateTimeDialog = false },
                        onSave = { timestamp ->
                            Log.d("PRIYANSHU", "DoctorProfile: TIMESTAMP $timestamp")
                            showDateTimeDialog = false
                            appointmentViewModel.bookAppointment(doctor.doctorId, timestamp)
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun PastHistory(prescriptions: List<Prescription>, onItemClick: (Prescription) -> Unit) {
    Text(
        text = "Past History",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentPadding = PaddingValues(bottom = 48.dp)
    ) {
        items(prescriptions) { prescription ->
            PrescriptionListItem(prescription, onItemClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionListItem(prescription: Prescription, onItemClick: (Prescription) -> Unit) {
    Card(
        onClick = {
            onItemClick(prescription)
        },
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, strokeColor),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = prescription.symptoms.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = prescription.diagnose.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = prescription.date.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}