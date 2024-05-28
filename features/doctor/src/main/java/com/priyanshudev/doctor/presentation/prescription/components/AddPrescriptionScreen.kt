package com.priyanshudev.doctor.presentation.prescription.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.itextpdf.kernel.colors.DeviceCmyk
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Div
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.font.FontProvider
import com.itextpdf.layout.font.FontSet
import com.itextpdf.layout.property.Property
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.priyanshudev.common.domain.model.Medicines
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.presentation.prescription.PrescriptionViewModel
import com.priyanshudev.doctor.theme.appBlue
import com.priyanshudev.doctor.theme.headerColor
import com.priyanshudev.doctor.theme.strokeColor
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddPrescriptionScreen(patient: Patient) {
    var symptomsText by remember { mutableStateOf("") }
    var observationText by remember { mutableStateOf("") }
    var diagnosisText by remember { mutableStateOf("") }
    val medicines = remember { mutableStateListOf<Medicines>() }

    val symptomsSpeechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 100 || result.data != null) {
            val res = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            res?.let {
                symptomsText = it[0]
            }
        }
    }
    val observationSpeechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 100 || result.data != null) {
            val res = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            res?.let {
                observationText = it[0]
            }
        }
    }
    val diagnosisSpeechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 100 || result.data != null) {
            val res = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            res?.let {
                diagnosisText = it[0]
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        Header()
        DateText()
        PrescriptionTextFields(
            textField = "Symptoms",
            symptomsText,
            { symptomsText = it },
            { speechToText("Symptoms", symptomsSpeechRecognizerLauncher) }
        )
        PrescriptionTextFields(
            textField = "Observation",
            observationText,
            { observationText = it },
            { speechToText("Observation", observationSpeechRecognizerLauncher) }
        )
        PrescriptionTextFields(
            textField = "Diagnosis",
            diagnosisText,
            { diagnosisText = it },
            { speechToText("Diagnosis", diagnosisSpeechRecognizerLauncher) }
        )
        AddMedicine(medicines)
        CreatePrescriptionButton(symptomsText, observationText, diagnosisText, medicines, patient)
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
fun PrescriptionTextFields(
    textField: String,
    value: String,
    onValueChanged: (text: String) -> Unit = {},
    onClickListener: () -> Unit = {}
) {
    var text by remember { mutableStateOf(TextFieldValue(value)) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            value = value,
            label = { Text(text = textField) },
            onValueChange = {
                onValueChanged(it)
            }, shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = appBlue,
                unfocusedBorderColor = strokeColor,
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_mic),
            contentDescription = null,
            modifier = Modifier
                .padding(15.dp)
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    onClickListener()
                }
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
fun AddMedicine(medicines:  MutableList<Medicines>) {
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
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .width(140.dp)
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
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.height(250.dp)
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
fun CreatePrescriptionButton(
    symptomsText: String,
    observationText: String,
    diagnosisText: String,
    medicines: List<Medicines>,
    patient: Patient,
    viewModel: PrescriptionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isReadPermissionGranted by remember { mutableStateOf(false) }
    var isWritePermissionGranted by remember { mutableStateOf(false) }
    var isManagePermissionGranted by remember { mutableStateOf(false) }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        isReadPermissionGranted = result[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        isWritePermissionGranted = result[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            isManagePermissionGranted = result[Manifest.permission.MANAGE_EXTERNAL_STORAGE] ?: false
        }
    }
    fun checkPermissions() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        isWritePermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            isManagePermissionGranted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            isManagePermissionGranted = true
        }
    }

    fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if (!isReadPermissionGranted) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!isWritePermissionGranted) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !isManagePermissionGranted) {
            permissionsToRequest.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }
        if (permissionsToRequest.isNotEmpty()) {
            permissionResultLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            Toast.makeText(context, "All permissions already granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkAndRequestPermissions() {
        checkPermissions()
        if (!isReadPermissionGranted || !isWritePermissionGranted || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !isManagePermissionGranted)) {
            requestPermissions()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                checkPermissions()

                if (isReadPermissionGranted || isWritePermissionGranted) {
                    val prescription = Prescription(
                        symptoms = symptomsText,
                        observation = observationText,
                        diagnose = diagnosisText,
                        medicines = medicines
                    )
                    createAndSharePdf(context, prescription, patient, viewModel)
                } else {
                    checkAndRequestPermissions()
                }
            },
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

fun speechToText(text: String, speechRecognizerLauncher: ActivityResultLauncher<Intent>) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "en");

        putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the $text")
    }
    speechRecognizerLauncher.launch(intent)
}

private fun createAndSharePdf(context: Context, prescription: Prescription, patient: Patient, viewModel: PrescriptionViewModel) {

    val filePath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.getExternalFilesDir(null)
    } else {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }


    val file = File(filePath, "prescription.pdf")
    val outputStream: OutputStream = FileOutputStream(file)
    val writer = PdfWriter(outputStream)
    val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(writer)
    val document = Document(pdfDocument)

    val fontSet = FontSet()
    fontSet.addFont("res/font/noto_sans_devanagari.ttf")
    document.fontProvider = FontProvider(fontSet)
    document.setProperty(Property.FONT, arrayOf("NotoSans"))
    pdfDocument.defaultPageSize = PageSize.A4
    document.setMargins(0F, 0F, 0F, 0F)


    val clinicInfo = Div()
        .add(Paragraph("Clinic Name\nAddress: Clinic Address\nPhone: Clinic Phone")
            .setFontSize(14f)
            .setTextAlignment(TextAlignment.CENTER))
        .setWidth(UnitValue.createPercentValue(100f))
        .setBackgroundColor(DeviceCmyk.CYAN)
    document.add(clinicInfo)

    // Add patient details
    val patientDetails = Paragraph(
        "Patient Name: ${patient.name}\nAge: ${patient.age}\n Phone: ${patient.number}"
    )
        .setFontSize(12f)
        .setMargin(20f)
    document.add(patientDetails)

    // Add symptoms
    val symptoms = Paragraph("Symptoms: ${prescription.symptoms}")
        .setFontSize(12f)
        .setMargin(20f)
    document.add(symptoms)

    // Add diagnosis
    val diagnosis = Paragraph("Diagnosis: ${prescription.diagnose}")
        .setFontSize(12f)
        .setMargin(20f)
    document.add(diagnosis)

    // Add observation
    val observation = Paragraph("Observation: ${prescription.observation}")
        .setFontSize(12f)
        .setMargin(20f)
    document.add(observation)

    // Add table of medicines
    val table = Table(UnitValue.createPercentArray(floatArrayOf(40f, 15f, 15f, 15f, 15f)))
        .setWidth(UnitValue.createPercentValue(100f))
        .setMargin(20f)
    table.addCell(Paragraph("Medicine").setTextAlignment(TextAlignment.CENTER))
    table.addCell(Paragraph("Days").setTextAlignment(TextAlignment.CENTER))
    table.addCell(Paragraph("Morning").setTextAlignment(TextAlignment.CENTER))
    table.addCell(Paragraph("Afternoon").setTextAlignment(TextAlignment.CENTER))
    table.addCell(Paragraph("Night").setTextAlignment(TextAlignment.CENTER))
    // Add more rows for medicines

    for (medicine in prescription.medicines) {
        table.addCell(Paragraph(medicine.medicineName).setTextAlignment(TextAlignment.CENTER))
        table.addCell(Paragraph("${medicine.duration}").setTextAlignment(TextAlignment.CENTER))
        table.addCell(Paragraph(medicine.morningDosage).setTextAlignment(TextAlignment.CENTER))
        table.addCell(Paragraph(medicine.afternoonDosage).setTextAlignment(TextAlignment.CENTER))
        table.addCell(Paragraph(medicine.nightDosage).setTextAlignment(TextAlignment.CENTER))
    }

    // Add more rows as needed
    document.add(table)
    document.close()
    val presc = Prescription(
        "doctorName",
        prescription.symptoms,
        prescription.observation,
        prescription.diagnose,
        prescription.medicines,
        System.currentTimeMillis().toString(),
        "doctorId",
        patient.patientId
    )
    Log.d("PRIYANSHU", "createAndSharePdf: $presc ")
    viewModel.addPrescription(presc)
    sharePdf(context, file)
}

private fun sharePdf(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "com.priyanshudev.medconnect.fileprovider", file)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Share PDF using"))
}
