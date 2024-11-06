package com.priyanshudev.patient.presentation.main.home

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyanshudev.common.domain.model.Doctor
import com.priyanshudev.patient.domain.qrcode.QrCode
import com.priyanshudev.patient.presentation.main.viewmodel.HomeViewModel
import com.priyanshudev.patient.theme.headerColor

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onShareClick: () -> Unit  = {},
    onItemClick:(Doctor) -> Unit
) {
    val searchText by viewModel.searchText.collectAsState()
    val doctors by viewModel.doctors.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(onShareClick)
        SearchTextView(
            searchText,
            viewModel::onSearchTextChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        DoctorsList(doctors,onItemClick)
    }
}

@Composable
fun Header(onShareClick: () -> Unit = {}){
    var showQrDialog by remember { mutableStateOf(false) }
    var qrCodeBitmap by remember { mutableStateOf<Bitmap?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0,0,15,15),
        colors = CardDefaults.cardColors(
            containerColor = headerColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Column {
                Text(
                    modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 0.dp),
                    text = "Hello,",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 15.dp),
                    text = "Priyanshu Borole",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Icon(
                Icons.Default.Share,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .clickable {
                        showQrDialog = true
                        qrCodeBitmap = QrCode.generateQrCode("patient ID")
                    }
            )
        }
    }
    if (showQrDialog && qrCodeBitmap != null) {
        AlertDialog(
            onDismissRequest = { showQrDialog = false },
            title = { Text(text = "Generated QR Code") },
            text = {
                qrCodeBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showQrDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun SearchTextView(
    searchText: String,
    onSearchTextChange: (String) -> Unit
){
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        placeholder = {
            Text(text = "Search")
        },
        label = {
                Text(text = "Search")
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (searchText != "") {
                IconButton(
                    onClick = {
                        onSearchTextChange("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = headerColor,
            unfocusedContainerColor = headerColor
        )
    )
}

@Composable
fun DoctorsList(doctors: List<Doctor>, onItemClick:(Doctor) -> Unit){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentPadding = PaddingValues(bottom = 48.dp)
    ) {
        items(doctors) { doctor ->
            DoctorListItem(doctor = doctor,onItemClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun PreviewHomeScreen(){
    Header()
}