package com.priyanshudev.doctor.presentation.prescription.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.priyanshudev.common.domain.model.Medicines
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.theme.appBlue
import com.priyanshudev.doctor.theme.headerColor
import com.priyanshudev.doctor.theme.strokeColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddPrescriptionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        Header()
        DateText()
        PrescriptionTextFields(textField = "Symptoms")
        PrescriptionTextFields(textField = "Observation")
        PrescriptionTextFields(textField = "Diagnosis")
        AddMedicine()
        CreatePrescriptionButton()
    }
}

@Preview
@Composable
fun Header() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0, 0, 15, 15),
        colors = CardDefaults.cardColors(
            containerColor = headerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Fill the patient details for prescription",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionTextFields(textField: String) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            label = { Text(text = textField) },
            onValueChange = {
                text = it
            }, shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appBlue,
                unfocusedBorderColor = strokeColor
            )
        )
    }
}

@Composable
fun DateText() {
    val todayDate = remember { LocalDate.now() }
    val formattedDate = remember {
        todayDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH))
    }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 16.dp, top = 12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Date of consultation: ",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = appBlue,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun AddMedicine() {
    val medicines = remember { mutableStateListOf<Medicines>() }
    val showDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
        Button(
            onClick = {
                showDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(
                headerColor, appBlue
            ),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp)),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "+ Add Medicine",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        AddMedicineTable(medicines = medicines)

        if (showDialog.value) {
            AddMedicineDialog(
                onAddMedicine = { medicine ->
                    medicines.add(
                        Medicines(
                            medicineName = medicine.medicineName,
                            duration = medicine.duration,
                            morning = medicine.morning ?: false,
                            afternoon = medicine.afternoon ?: false,
                            night = medicine.night ?: false,
                            morningDosage = medicine.morningDosage,
                            afternoonDosage = medicine.afternoonDosage,
                            nightDosage = medicine.nightDosage
                        )
                    )
                    showDialog.value = false
                },
                onCloseDialog = { showDialog.value = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineDialog(
    onAddMedicine: (Medicines) -> Unit,
    onCloseDialog: () -> Unit
) {
    val options = listOf("1 Day", "2 Days", "3 Days", "4 Days", "5 Days", "6 Days", "1 Week", "2 Weeks", "3 Weeks", "1 Month", "2 Months", "3 Months")
    var expanded by remember { mutableStateOf(false) }
    var medicineName by remember { mutableStateOf("") }
    var beforeMeal by remember { mutableStateOf(false) }
    var afterMeal by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf("") }
    var morning by remember { mutableStateOf(false) }
    var afternoon by remember { mutableStateOf(false) }
    var night by remember { mutableStateOf(false) }
    var morningDosage by remember { mutableStateOf("") }
    var afternoonDosage by remember { mutableStateOf("") }
    var nightDosage by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onCloseDialog,
        content = {
            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_rx),
                        contentDescription = "rx button",
                        modifier = Modifier
                            .width(22.dp)
                            .height(34.dp)
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = medicineName,
                        onValueChange = { medicineName = it },
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Medicine Name") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = appBlue,
                            unfocusedBorderColor = strokeColor
                        ), textStyle = MaterialTheme.typography.bodyMedium
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.padding(top = 10.dp).width(140.dp)
                    ) {
                        TextField(
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = duration,
                            onValueChange = {},
                            label = { Text("Days") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }, modifier = Modifier.height(250.dp)
                        ) {
                            options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        duration = selectionOption.toString()
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = beforeMeal,
                            onClick = { beforeMeal = true }
                        )
                        Text("Before meal", modifier = Modifier.align(Alignment.CenterVertically))
                        RadioButton(
                            selected = afterMeal,
                            onClick = { afterMeal = true }
                        )
                        Text("After meal", modifier = Modifier.align(Alignment.CenterVertically))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TimeDosageFields(
                        text = "Morning",
                        onClick = { morning = it },
                        onDosageChanged = {
                            morningDosage = it
                        })
                    Spacer(modifier = Modifier.height(10.dp))

                    TimeDosageFields(
                        text = "Afternoon",
                        onClick = { afternoon = it },
                        onDosageChanged = {
                            afternoonDosage = it
                        })

                    Spacer(modifier = Modifier.height(10.dp))

                    TimeDosageFields(text = "Night", onClick = { night = it }, onDosageChanged = {
                        nightDosage = it
                    })
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                val newMedicine = Medicines(
                                    medicineName = medicineName,
                                    duration = 3,
                                    morning = morning,
                                    afternoon = afternoon,
                                    night = night,
                                    morningDosage = morningDosage,
                                    afternoonDosage = afternoonDosage,
                                    nightDosage = nightDosage
                                )
                                onAddMedicine(newMedicine)
                                onCloseDialog()
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Add")
                        }
                        Button(
                            onClick = onCloseDialog
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TimeDosageFields(
    text: String,
    onClick: (Boolean) -> Unit,
    onDosageChanged: (String) -> Unit
) {
    val isSelected = remember {
        mutableStateOf(false)
    }
    val dosage = remember {
        mutableStateOf("")
    }
    val strokeColor = if (isSelected.value) appBlue else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Spacer(modifier = Modifier.width(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .border(2.dp, strokeColor, RoundedCornerShape(10.dp))
                .background(Color.White, RoundedCornerShape(10.dp))
                .shadow(18.dp, RectangleShape)
                .weight(0.75f)
        ) {
            Button(
                onClick = {
                    if (isSelected.value) {
                        isSelected.value = false
                        onClick.invoke(false)
                    } else {
                        isSelected.value = true
                        onClick.invoke(true)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(Color.White, Color.Black)
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .weight(0.25f)
                .border(2.dp, appBlue, RoundedCornerShape(10.dp))
                .align(Alignment.CenterVertically)
                .height(40.dp)
        ) {
            BasicTextField(
                value = dosage.value,
                onValueChange = {
                    dosage.value = it
                    onDosageChanged.invoke(dosage.value)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    lineHeight = 80.sp
                )
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Composable
fun CreatePrescriptionButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                appBlue, Color.White
            ),
            modifier = Modifier.padding(bottom = 10.dp, start = 16.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Create Prescription",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun AddMedicineTable(medicines: List<Medicines>) {
    var index = 1
    Column(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        medicines.forEach { medicine ->
            if (index == 1) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(40.dp)
                        .background(appBlue),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = " ",
                        modifier = Modifier
                            .width(40.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color.White)
                    )
                    Text(
                        text = "Medicine",
                        modifier = Modifier
                            .width(150.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color.White)
                    )
                    Text(
                        text = "Days",
                        modifier = Modifier
                            .width(105.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color.White)
                    )
                    Text(
                        text = "Morning",
                        modifier = Modifier
                            .width(120.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color.White)
                    )
                    Text(
                        text = "Afternoon",
                        modifier = Modifier
                            .width(120.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color.White)
                    )
                    Text(
                        text = "Night",
                        modifier = Modifier
                            .width(120.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(30.dp)
                    .background(headerColor),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$index",
                    modifier = Modifier.width(40.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color.White)
                )
                Text(
                    text = medicine.medicineName ?: "",
                    modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.Black, textAlign = TextAlign.Center
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color.White)
                )
                Text(
                    text = "${medicine.duration}" ?: "",
                    modifier = Modifier
                        .width(105.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.Black, textAlign = TextAlign.Center
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color.White)
                )
                Text(
                    text = "${medicine.morningDosage}" ?: "",
                    modifier = Modifier
                        .width(120.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.Black, textAlign = TextAlign.Center
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color.White)
                )
                Text(
                    text = "${medicine.afternoonDosage}" ?: "",
                    modifier = Modifier
                        .width(120.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.Black, textAlign = TextAlign.Center
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color.White)
                )
                Text(
                    text = "${medicine.nightDosage}" ?: "",
                    modifier = Modifier
                        .width(120.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.Black, textAlign = TextAlign.Center
                )
            }
            index++;
        }
    }
}