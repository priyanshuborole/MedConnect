package com.priyanshudev.patient.presentation.appointment.component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.priyanshudev.patient.R
import com.priyanshudev.patient.theme.strokeColor
import java.util.Calendar

@Composable
fun DateTimePickerDialog(
    onDismissRequest: () -> Unit,
    onSave: (Long) -> Unit
) {
    val context = LocalContext.current

    // State variables to hold date and time
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(context, { _, y, m, d ->
        selectedDate = "$d/${m + 1}/$y"
        calendar.set(y, m, d)
    }, year, month, day)

    val timePickerDialog = TimePickerDialog(context, { _, h, m ->
        selectedTime = "$h:$m"
        calendar.set(Calendar.HOUR_OF_DAY, h)
        calendar.set(Calendar.MINUTE, m)
    }, hour, minute, true)

    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties()) {
        Surface(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onDismissRequest, modifier = Modifier.align(Alignment.End)) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    modifier = Modifier
                        .height(191.dp)
                        .width(156.dp),
                    painter = painterResource(id = R.drawable.ic_book_appointment),
                    contentDescription = null
                )
                OutlinedButton(
                    border = BorderStroke(1.dp, strokeColor),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        datePickerDialog.show()
                    }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Date")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (selectedDate.isEmpty()) "Select Date" else "Selected Date: $selectedDate")
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    border = BorderStroke(1.dp, strokeColor),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                    timePickerDialog.show()
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_clock),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentDescription = "Date"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (selectedTime.isEmpty()) "Select Time" else "Selected Time: $selectedTime")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    onSave(calendar.timeInMillis)
                    onDismissRequest()
                }) {
                    Text("Book")
                }
            }
        }
    }
}