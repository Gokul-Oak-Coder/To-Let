package com.example.to_let

import android.media.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.to_let.viewmodel.PropertyViewModel
import androidx.navigation.NavHostController
import com.example.to_let.model.Properties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng

@Composable
fun PropertyAddScreen(navController: NavHostController, viewModel: PropertyViewModel) {
    var propertyName by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var rentalAmount by remember { mutableStateOf("") }
    var yearOld by remember { mutableStateOf("") }
    var sharing by remember { mutableStateOf(false) }
    var selectedImages by remember { mutableStateOf<List<Image>>(emptyList()) }

    // Placeholder for the map selection
    var mapLocation by remember { mutableStateOf("Select Location") }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }

    Scaffold(
        /*topBar = {
            CenterAlignedTopAppBar(title = { Text("Add Property Details") })
        },*/
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Create a new Property object
                val newProperty = Properties(
                    id = id,
                    address = propertyName,
                    rentalAmount = rentalAmount,
                    occupied = sharing, // Assuming sharing is for PG/Rental status
                    images = selectedImages // Images would need to be added to the model
                )
                // Add the new property to the ViewModel
                viewModel.addProperty(newProperty)

                // Navigate back to the previous screen (AddPropertyScreen)
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Save, contentDescription = "Save Property")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    label = { Text("Property Id") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                // Property Name
                OutlinedTextField(
                    value = propertyName,
                    onValueChange = { propertyName = it },
                    label = { Text("Property Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Property Location
                Button(
                    onClick = {
                        navController.navigate("map_screen")
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = mapLocation)
                }

                // Property Images
                Text("Property Images", modifier = Modifier.padding(16.dp))
                Button(
                    onClick = { /* Open image picker */ },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Select Images")
                }

                // Rental Amount
                OutlinedTextField(
                    value = rentalAmount,
                    onValueChange = { rentalAmount = it },
                    label = { Text("Rental Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Year Old
                OutlinedTextField(
                    value = yearOld,
                    onValueChange = { yearOld = it },
                    label = { Text("Year Old") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Sharing Options for PG and Room
                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Sharing (if PG/Room)")
                    Checkbox(
                        checked = sharing,
                        onCheckedChange = { sharing = it },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Add more fields as needed
            }
        }
    }
}
