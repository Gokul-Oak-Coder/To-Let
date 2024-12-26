package com.example.to_let.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.to_let.ui.theme.ToLetTheme

class SignupActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val role = intent.getStringExtra("ROLE")

        setContent {
            ToLetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // SignupScreen(role!!)
                }
            }
        }
    }
}
/*
@Composable
fun SignupScreen(role: String) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<String?>(null) }
    var selectedLanguage by remember { mutableStateOf<String>("") }
    var expanded by remember { mutableStateOf(false) }

    val languageList = listOf("Tamil", "Malayalam", "Telugu", "Kannada", "Hindi")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Signup", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Picture with Rounded Image View
        if (profileImageUri != null) {
            Image(
                painter = rememberImagePainter(profileImageUri),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape), // Rounded Image
                contentScale = ContentScale.Crop
            )
        } else {
            // Default Icon or Placeholder for profile picture
            IconButton(
                onClick = { launchImagePicker { uri -> profileImageUri = uri } }
            ) {
                Icon(
                    imageVector = Icons.Filled.AddAPhoto,
                    contentDescription = "Pick profile image"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Known Languages Dropdown
        Text("Select Known Language", style = MaterialTheme.typography.h6)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedLanguage,
                onValueChange = { },
                label = { Text("Select Language") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                languageList.forEach { language ->
                    DropdownMenuItem(onClick = {
                        selectedLanguage = language
                        expanded = false
                    }) {
                        Text(text = language)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                if (username.isBlank() || email.isBlank() || selectedLanguage.isBlank()) {
                    Toast.makeText(LocalContext.current, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    if(role == "tenant"){
                        navigateToTenantHome()
                    }else{
                        navigateToOwnerHome()
                    }

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}


fun navigateToTenantHome() {
    val context = LocalContext.current
    val intent = Intent(context, TenantHomeActivity::class.java)
    context.startActivity(intent)
}

fun navigateToOwnerHome() {
    val context = LocalContext.current
    val intent = Intent(context, OwnerHomeActivity::class.java)
    context.startActivity(intent)
}
fun launchImagePicker(onImageSelected: (String) -> Unit) {
    ImagePicker.with(LocalContext.current)
        .crop() // Optionally add cropping
        .start { resultCode, data ->
            if (resultCode == android.app.Activity.RESULT_OK) {
                val uri = data?.data?.toString()
                uri?.let { onImageSelected(it) }
            }
        }
}

*/
/*
@Composable
@Preview
fun PreviewSignupScreen() {
    ToLetTheme {
        SignupScreen()
    }
}*/

