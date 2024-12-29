package com.example.to_let

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MapActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val geocoder = Geocoder(this)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getCurrentLocation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapScreen()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                // Use location
            }
        }
    }

    @Composable
    fun MapScreen() {
        var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        val context = LocalContext.current

        // Get the current location of the user
        val locationPermissionGranted = remember { mutableStateOf(false) }
       // val mapState = rememberMapState()

        LaunchedEffect(Unit) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Location") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            // Search Action
            LaunchedEffect(searchQuery.text) {
                if (searchQuery.text.isNotEmpty()) {
                    val geoResults = geocoder.getFromLocationName(searchQuery.text, 1)
                    geoResults?.firstOrNull()?.let {
                        selectedLatLng = LatLng(it.latitude, it.longitude)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Google Map
            /*GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                uiSettings = MapUiSettings(zoomControlsEnabled = true),
                onMapClick = { latLng -> selectedLatLng = latLng }
            ) {
                selectedLatLng?.let {
                    MarkerOptions().position(it).title("Selected Location")
                }

                // Set initial camera position if available
                selectedLatLng?.let {
                    CameraUpdateFactory.newLatLngZoom(it, 15f)
                }
            }*/

            // Bottom Bar to show location result
            if (selectedLatLng != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val resultIntent = Intent().apply {
                            putExtra("selected_location", selectedLatLng)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Select Location")
                }
            }
        }
    }
}
