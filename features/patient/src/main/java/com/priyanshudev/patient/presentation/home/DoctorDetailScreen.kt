package com.priyanshudev.patient.presentation.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.common.domain.model.Medicines
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.patient.R
import com.priyanshudev.patient.theme.headerColor
import com.priyanshudev.patient.theme.strokeColor

val prescriptionList = listOf(
    Prescription(
        doctorName = "Dr. Smith",
        symptoms = "Headache",
        diagnose = "Migraine",
        medicines = listOf(
            Medicines(
                medicineName = "Paracetamol",
                duration = 5,
                morning = true,
                afternoon = false,
                night = true,
                dosage = "500mg"
            ),
            Medicines(
                medicineName = "Ibuprofen",
                duration = 3,
                morning = true,
                afternoon = true,
                night = false,
                dosage = "200mg"
            )
        ),
        date = "2024-04-18",
        doctorId = "D123",
        patientId = "P456"
    ),
    Prescription(
        doctorName = "Dr. Johnson",
        symptoms = "Fever",
        diagnose = "Flu",
        medicines = listOf(
            Medicines(
                medicineName = "Aspirin",
                duration = 7,
                morning = true,
                afternoon = true,
                night = true,
                dosage = "300mg"
            ),
            Medicines(
                medicineName = "Antibiotics",
                duration = 10,
                morning = true,
                afternoon = true,
                night = true,
                dosage = "Varies"
            )
        ),
        date = "2024-04-19",
        doctorId = "D124",
        patientId = "P457"
    ),
    Prescription(
        doctorName = "Dr. Martinez",
        symptoms = "Sore throat",
        diagnose = "Tonsillitis",
        medicines = listOf(
            Medicines(
                medicineName = "Antiseptic spray",
                duration = 7,
                morning = true,
                afternoon = true,
                night = true,
                dosage = "As needed"
            ),
            Medicines(
                medicineName = "Cough syrup",
                duration = 5,
                morning = false,
                afternoon = false,
                night = true,
                dosage = "10ml"
            )
        ),
        date = "2024-04-20",
        doctorId = "D125",
        patientId = "P458"
    ),
    Prescription(
        doctorName = "Dr. Brown",
        symptoms = "Stomach pain",
        diagnose = "Gastritis",
        medicines = listOf(
            Medicines(
                medicineName = "Antacid",
                duration = 7,
                morning = true,
                afternoon = true,
                night = true,
                dosage = "As needed"
            ),
            Medicines(
                medicineName = "Digestive enzyme",
                duration = 10,
                morning = true,
                afternoon = true,
                night = true,
                dosage = "Varies"
            )
        ),
        date = "2024-04-21",
        doctorId = "D126",
        patientId = "P459"
    ),
    Prescription(
        doctorName = "Dr. Lee",
        symptoms = "Allergy",
        diagnose = "Seasonal allergies",
        medicines = listOf(
            Medicines(
                medicineName = "Antihistamine",
                duration = 14,
                morning = true,
                afternoon = false,
                night = true,
                dosage = "10mg"
            ),
            Medicines(
                medicineName = "Nasal spray",
                duration = 7,
                morning = false,
                afternoon = false,
                night = true,
                dosage = "As needed"
            )
        ),
        date = "2024-04-22",
        doctorId = "D127",
        patientId = "P460"
    )
)

@Composable
fun DoctorDetailScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    doctor: Doctor,
    onItemClick: (Prescription) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DoctorProfile(doctor)
        Spacer(modifier = Modifier.height(16.dp))
        PastHistory(prescriptions = prescriptionList, onItemClick)
    }
}

@Composable
fun DoctorProfile(doctor: Doctor) {
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
                text = prescription.medicines.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}