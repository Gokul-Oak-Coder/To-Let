package com.example.to_let

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.to_let.viewmodel.PropertyViewModel

@Composable
fun PropertyEditScreen(
    navController: NavHostController,
    propertyId: String, // We are passing property id for navigation
    viewModel: PropertyViewModel
) {
    val property =
        viewModel.getPropertyById(propertyId) // Assuming you have a method to fetch by ID
    var propertyName by remember { mutableStateOf(property?.address ?: "") }
    var rentalAmount by remember { mutableStateOf(property?.rentalAmount ?: "") }
    var sharing by remember { mutableStateOf(property?.occupied ?: false) }

    Scaffold(
        /*topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Property") }
            )
        },*/
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Update property in ViewModel
                val updatedProperty = property?.copy(
                    address = propertyName,
                    rentalAmount = rentalAmount,
                    occupied = sharing
                )
                viewModel.updateProperty(updatedProperty!!)
                navController.popBackStack() // Navigate back
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
                    value = propertyName,
                    onValueChange = { propertyName = it },
                    label = { Text("Property Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = rentalAmount,
                    onValueChange = { rentalAmount = it },
                    label = { Text("Rental Amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Sharing (if PG/Room)")
                    Checkbox(
                        checked = sharing,
                        onCheckedChange = { sharing = it },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Add delete button
                Button(
                    onClick = {
                        viewModel.deleteProperty(property!!)
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Delete Property")
                }
            }
        }
    }
}
