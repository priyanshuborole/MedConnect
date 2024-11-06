package com.priyanshudev.doctor.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.theme.strokeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListItem(patient: Patient, onItemClick:(Patient) -> Unit){
    Card(
        onClick = {
            onItemClick(patient)
        },
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, strokeColor),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_doctor_male),
                contentDescription = "Profile Image",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = patient.name,
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = patient.patientId,
                    style = typography.bodyMedium
                )
            }
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewDoctorListItem(){
//    DoctorListItem()
}