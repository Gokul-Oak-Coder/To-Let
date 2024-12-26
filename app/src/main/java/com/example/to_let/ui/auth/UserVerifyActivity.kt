package com.example.to_let.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.to_let.R
import com.example.to_let.viewmodel.SplashViewModel
import com.example.to_let.ui.theme.ToLetTheme
import com.google.firebase.auth.FirebaseAuth

class UserVerifyActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashViewModel>()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }
        setContent {
            ToLetTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OtpSendScreen(auth)
                }
            }
        }
    }
}

@Composable
fun OtpSendScreen(auth: FirebaseAuth) {
    val maxLength = 10
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current // Get the context
    var selectedRole by remember { mutableStateOf("tenant") }  // Default role is "tenant"
    var verificationId by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_plane),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp, 200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Choose what are you going to occupy and enter phone number.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(), // Fill the width of the parent
            textAlign = TextAlign.Center // Center the text horizontally
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We will send otp to your entered mobile number.",
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedRole == "tenant",
                    onClick = { selectedRole = "tenant" },
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Tenant", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterVertically))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedRole == "owner",
                    onClick = { selectedRole = "owner" },
                            modifier = Modifier.size(30.dp)
                )
                Text(text = "Owner", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Added some padding to the overall row for spacing
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country Code Input (e.g., +91)
            OutlinedTextField(
                value = "+91",
                onValueChange = {},
                modifier = Modifier
                    .width(70.dp),
                placeholder = { Text("+91") },
                textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), // Custom text size
                readOnly = true,  // Since it's the country code, it doesn't need to be editable
                maxLines = 1
            )

            // Phone Number Input
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { text ->
                    if (text.length <= maxLength) {
                        phoneNumber = text
                    }
                },
                modifier = Modifier
                    .weight(1f)  // The number input will take up the remaining space
                    .padding(start = 2.dp), // Added padding between the inputs
                placeholder = { Text("0987654321") },
                textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), // Custom text size
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                maxLines = 1
            )
        }


        Spacer(modifier = Modifier.height(45.dp))

        Button(
            onClick = {
                if(phoneNumber.isNotBlank()){
                    val intent = Intent(context, VerifyOTPActivity::class.java)
                    intent.putExtra("PHONE_NUMBER", phoneNumber)
                    intent.putExtra("ROLE", selectedRole)
                    context.startActivity(intent)
                }
                /*if (phoneNumber.isNotBlank() && phoneNumber.length == 10) {
                    isLoading = true
                    val phoneNumberWithCountryCode = "+91$phoneNumber"
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumberWithCountryCode)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)                // Timeout duration
                        .setActivity(context as UserVerifyActivity)       // Current activity
                        .setCallbacks(object :
                            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                // Auto-verification completed
                                val code = credential.smsCode
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                Toast.makeText(
                                    context,
                                    "Verification failed: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isLoading = false
                            }

                            override fun onCodeSent(
                                verificationId: String,
                                token: PhoneAuthProvider.ForceResendingToken
                            ) {
                                // Code sent, now use the verificationId for the next step
                                Toast.makeText(
                                    context,
                                    "Verification successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Proceed with OTP verification
                                val intent = Intent(context, VerifyOTPActivity::class.java)
                                intent.putExtra("PHONE_NUMBER", phoneNumber)
                                intent.putExtra("ROLE", selectedRole)
                                intent.putExtra("TOKEN", token)
                                intent.putExtra("VERIFICATION_ID", verificationId)
                                context.startActivity(intent)
                                Toast.makeText(context, "OTP sent", Toast.LENGTH_SHORT).show()
                                isLoading = false
                            }
                        })
                        .build()

                    PhoneAuthProvider.verifyPhoneNumber(options)
                } else {
                    Toast.makeText(
                        context,
                        "Please fill the Phone number field",
                        Toast.LENGTH_SHORT
                    ).show()
                }*/

            },
            modifier = Modifier
                .width(200.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "SEND",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(45.dp))

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally),
                strokeWidth = 4.dp,
            )
        }
    }
}
