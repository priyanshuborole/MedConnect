package com.priyanshudev.doctor.presentation.home.components

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyanshudev.common.domain.model.Patient
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.presentation.home.DoctorViewModel
import com.priyanshudev.doctor.theme.headerColor
import com.priyanshudev.doctor.theme.textDefault

@Composable
fun HomeScreen(
    viewModel: DoctorViewModel = hiltViewModel(),
    onItemClick:(Patient) -> Unit,
    onScanClick: () -> Unit
) {
    val searchText by viewModel.searchText.collectAsState()
    val doctors by viewModel.patient.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(onScanClick)
        SearchTextView(
            searchText,
            viewModel::onSearchTextChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Your Patients", modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Start)
            .padding(start = 16.dp), fontSize = 16.sp, color = textDefault)
        PatientsList(doctors,onItemClick)
    }
}

@Composable
fun Header(onClick: () -> Unit){
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Text(
                    modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 0.dp),
                    text = "Hello,",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 15.dp),
                    text = "Vibhuti Patil",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_qr_code_scanner),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .clickable {
                        onClick()
                    }
            )

        }
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
fun PatientsList(patients: List<Patient>, onItemClick:(Patient) -> Unit){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentPadding = PaddingValues(bottom = 48.dp)
    ) {
        items(patients) { patient ->
            PatientListItem(patient = patient,onItemClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun PreviewHomeScreen(){
//    Header()
}