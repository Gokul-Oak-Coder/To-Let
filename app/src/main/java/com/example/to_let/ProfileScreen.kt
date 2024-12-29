package com.example.to_let

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.to_let.model.UserProfile

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen() {
    var userProfile by remember {
        mutableStateOf(
            UserProfile(
                imageUrl = R.drawable.ic_me,
                username = "John Doe",
                phoneNumber = "+91 6345658778",
                location = "Dharmapuri",
                knownLanguages = listOf("Tamil", "English", "Kannada"),
                dob = "07-02-2000"
            )
        )
    }
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp), contentAlignment = Alignment.Center
        ) {
            var showLanguagesDialog by remember { mutableStateOf(false) }
            var selectedLanguages by remember { mutableStateOf(userProfile.knownLanguages) }

            var showLogoutConfirmation by remember { mutableStateOf(false) }

            Column(modifier = Modifier.fillMaxSize()) {
                // Image Card with Blur Effect
                //ImageWithBlur(imageUrl = userProfile.imageUrl)
                ImageWithBlur(imageUrl = R.drawable.ic_me)

                // Profile Information Section
                ProfileInfo(
                    username = userProfile.username,
                    location = userProfile.location,
                    phoneNumber = userProfile.phoneNumber,
                    knownLanguages = selectedLanguages,
                    dob = userProfile.dob,
                    onLanguagesSelect = {
                        selectedLanguages = it
                    }
                )

                // Logout Button
                Spacer(modifier = Modifier.weight(1f))
                LogoutButton(
                    onLogoutClick = {
                        showLogoutConfirmation = true
                    }
                )

                // Logout Confirmation Dialog
                if (showLogoutConfirmation) {
                    LogoutDialog(onConfirm = {

                    }, onDismiss = {
                        showLogoutConfirmation = false
                    })
                }
            }
        }
    }
}
@Composable
fun ImageWithBlur(imageUrl: Int) {
    Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        // Image with blur effect
        Image(
            painter = painterResource(R.drawable.ic_me),
            contentDescription = "Profile Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )
    }
}

@Composable
fun ProfileInfo(
    username: String,
    phoneNumber: String,
    location: String,
    knownLanguages: List<String>,
    dob: String,
    onLanguagesSelect: (List<String>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(text = "Username: $username", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Phone Number: $phoneNumber", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Location: $location", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Date of Birth: $dob", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        // Known Languages
        Text(text = "Known Languages: ${knownLanguages.joinToString(", ")}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Button to select languages
        Button(onClick = { onLanguagesSelect(knownLanguages) }) {
            Text("Edit Known Languages")
        }
    }
}

@Composable
fun LogoutButton(onLogoutClick: () -> Unit) {
    Button(
        onClick = onLogoutClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.error)
    ) {
        Text("Logout", color = Color.Blue)
    }
}

@Composable
fun LogoutDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Are you sure you want to log out?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}

@Composable
fun LanguageSelectionDialog(
    selectedLanguages: List<String>,
    onDismiss: () -> Unit,
    onLanguagesSelected: (List<String>) -> Unit
) {
    val allLanguages = listOf("Tamil", "Hindi", "Malayalam", "Kannada", "Telugu")
    val tempSelectedLanguages = remember { mutableStateListOf(*selectedLanguages.toTypedArray()) }

    androidx.compose.material.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Known Languages") },
        buttons = {
            Column {
                allLanguages.forEach { language ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Checkbox(
                            checked = tempSelectedLanguages.contains(language),
                            onCheckedChange = {
                                if (it) {
                                    tempSelectedLanguages.add(language)
                                } else {
                                    tempSelectedLanguages.remove(language)
                                }
                            }
                        )
                        Text(text = language)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    TextButton(onClick = {
                        onLanguagesSelected(tempSelectedLanguages)
                        onDismiss()
                    }) {
                        Text("Done")
                    }
                }
            }
        }
    )
}