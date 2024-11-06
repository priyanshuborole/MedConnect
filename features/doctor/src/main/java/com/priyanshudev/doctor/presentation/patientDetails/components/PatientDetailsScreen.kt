package com.priyanshudev.doctor.presentation.patientDetails.components

import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.presentation.home.DoctorViewModel
import com.priyanshudev.doctor.theme.appBlue
import com.priyanshudev.doctor.theme.headerColor
import com.priyanshudev.doctor.theme.strokeColor
import com.priyanshudev.doctor.theme.textDefault

@Composable
fun PatientDetailsScreen(
    patient: Patient, viewModel: DoctorViewModel = hiltViewModel(),
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            PatientProfile(patient)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Patient History", modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Start)
                    .padding(start = 16.dp), fontSize = 16.sp, color = textDefault
            )
            PrescriptionList(viewModel, onItemClick)
        }
        BottomNavButtons(onItemClick)
    }
}

@Composable
fun PrescriptionList(viewModel: DoctorViewModel, onItemClick: () -> Unit) {
    viewModel.getPrescription()
    val prescriptions by viewModel.prescriptions.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentPadding = PaddingValues(bottom = 48.dp),

    ) {
        items(prescriptions) { prescription ->
            PrescriptionListItem(prescription, onItemClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun PatientProfile(patient: Patient) {
    val genderImage = R.drawable.ic_male
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

                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = patient.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black
                    )
                    Image(
                        painter = painterResource(genderImage),
                        contentDescription = "Gender image",
                        modifier = Modifier
                            .size(22.dp)
                            .padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${patient.number}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${patient.age} Years old",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Blood group: ${patient.bloodGroup}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Address: ${patient.address}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionListItem(prescription: Prescription, onItemClick: () -> Unit) {
    Card(
        onClick = {
            onItemClick()
        },
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, strokeColor),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(
            text = prescription.diagnose ?: "",
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Start)
                .padding(vertical = 6.dp, horizontal = 14.dp),
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
        prescription.medicines.forEach { medicine ->
            Text(
                text = medicine.medicineName ?: "",
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Start)
                    .padding(vertical = 2.dp, horizontal = 14.dp),
                fontSize = 12.sp,
                color = textDefault,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Start)
            .padding(vertical = 6.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id =R.drawable.ic_time), contentDescription = "Recent icon", Modifier.size(14.dp))
            Text(
                text = prescription.date ?: "",
                modifier = Modifier
                    .wrapContentSize(),
                fontSize = 12.sp,
                color = textDefault,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BottomNavButtons(onItemClick: () -> Unit){
//    Box(
//        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
//        contentAlignment = Alignment.BottomCenter
//    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(modifier = Modifier.wrapContentSize().padding(horizontal = 10.dp)) {
            Button(
                onClick = { onItemClick() },
                modifier = Modifier
                    .height(60.dp)
                    .wrapContentWidth()
                    .padding(start = 16.dp), shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    appBlue, Color.White // Set the background color here
                )
            ) {
                Image(
                    painterResource(id = R.drawable.ic_add),
                    contentDescription = "add prescription button",
                    modifier = Modifier.size(20.dp)
                )

                Text(text = "Add Prescription", style = MaterialTheme.typography.bodySmall)
            }
        }
        Box(modifier = Modifier.wrapContentSize().padding(horizontal = 10.dp)) {
            Button(
                onClick = {},
                modifier = Modifier
                    .height(60.dp)
                    .wrapContentWidth(), shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    appBlue, Color.White // Set the background color here
                )
            ) {
                Image(
                    painterResource(id = R.drawable.ic_call),
                    contentDescription = "Call button",
                    modifier = Modifier.size(20.dp)
                )

                Text(text = "Call", style = MaterialTheme.typography.bodySmall)
            }
        }
//        Button(onClick = {}, modifier = Modifier.size(60.dp).wrapContentWidth()) {
        Image(
            painterResource(id = R.drawable.ic_whatsapp),
            contentDescription = "whatsapp button",
            modifier = Modifier.size(60.dp)
        )
//        }
//    }
    }
    }


@Composable
@Preview(showSystemUi = true)
fun PreviewDoctorListItem() {
//    DoctorListItem()
}
