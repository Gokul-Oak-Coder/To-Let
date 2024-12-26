package com.example.to_let.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_let.ui.activities.OwnerHomeActivity
import com.example.to_let.R
import com.example.to_let.ui.activities.TenantHomeActivity
import com.example.to_let.ui.theme.ToLetTheme
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class VerifyOTPActivity : ComponentActivity() {

    private var verificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val phoneNumber = intent.getStringExtra("PHONE_NUMBER")
        val role = intent.getStringExtra("ROLE")
        /*resendToken = intent.getParcelableExtra("TOKEN")
        verificationId = intent.getStringExtra("VERIFICATION_ID")*/

        startSmsRetriever()

        setContent {
            ToLetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // OtpVerifyScreen(phoneNumber!!, role!!, auth, verificationId!!)
                    OtpVerifyScreen(phoneNumber!!, role!!)
                }
            }
        }
    }
    // Start SMS Retriever
    private fun startSmsRetriever() {
        val client: SmsRetrieverClient = SmsRetriever.getClient(this)
        val task: Task<Void> = client.startSmsRetriever()

        task.addOnSuccessListener {
            // Successfully started listening for OTP SMS
            Toast.makeText(this, "Listening for OTP", Toast.LENGTH_SHORT).show()
        }

        task.addOnFailureListener {
            // Failed to start SMS Retriever
            Toast.makeText(this, "Failed to start OTP listener", Toast.LENGTH_SHORT).show()
        }
    }

    // Callback for receiving OTP from SMS Retriever
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if (requestCode == SmsRetriever.SMS_RETRIVED) {
            val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
            val otp = extractOtpFromSms(message)
            otp?.let {
                // Set OTP in the input fields
                setOtpToFields(it)
            }
        }*/
    }

    // Extract OTP from the message
    private fun extractOtpFromSms(message: String?): String? {
        val pattern = "(\\d{6})"  // OTP is a 6-digit number
        val regex = Regex(pattern)
        val match = regex.find(message ?: "")
        return match?.value
    }

    // Set OTP to the OTP fields
    private fun setOtpToFields(otp: String) {
        val otpFields = otp.chunked(1).toList()
        otpFields.forEachIndexed { index, value ->
            // Assign values to each OTP field (modify OTP input fields to accept a list of fields)
           // otpFields[index].value = value
        }
    }
}

@Composable
fun OtpVerifyScreen(phoneNumber: String, role: String) {
    var otp by remember { mutableStateOf("") }
    var progressBarVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // ImageView (OTP Icon)
        Image(
            painter = painterResource(id = R.drawable.ic_otp),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp, 200.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Verify OTP Text
        Text(
            text = "Verify OTP",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Enter OTP instruction text
        Text(
            text = "Enter your received OTP here",
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Mobile number (for context)
        Text(
            text = "+91-$phoneNumber",
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally)
        )

        // OTP input fields (EditTexts)
        OTPInput()

        // Resend OTP Text
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Don't get the OTP?",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(1.dp))
            TextButton(
                onClick = {
                    /*if (resendToken != null) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,
                            60L,  // Timeout duration
                            TimeUnit.SECONDS,
                            context as VerifyOTPActivity,  // Activity context
                            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                    // Auto-verified
                                    auth.signInWithCredential(credential)
                                }

                                override fun onVerificationFailed(e: FirebaseException) {
                                    // OTP resend failed
                                    Toast.makeText(context, "Failed to resend OTP", Toast.LENGTH_SHORT).show()
                                }

                                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                                    super.onCodeSent(verificationId, token)
                                    // Update verification ID and resend token
                                    context as VerifyOTPActivity.verificationId = verificationId
                                    resendToken = token
                                }
                            })
                    }*/
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "RESEND OTP",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(45.dp))

        // Verify Button
        // Handle OTP verification logic
        Button(
            onClick = {
                //progressBarVisibility = true
                //val credential = PhoneAuthProvider.getCredential(verificationId, otp)

                if(role == "tenant"){
                    val intent = Intent(context, TenantHomeActivity::class.java)
                    intent.putExtra("PHONE_NUMBER", phoneNumber)
                    context.startActivity(intent)
                }
                else{
                    val intent = Intent(context, OwnerHomeActivity::class.java)
                    intent.putExtra("PHONE_NUMBER", phoneNumber)
                    context.startActivity(intent)
                }

                /*// Sign in with Firebase Authentication
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    progressBarVisibility = false
                    if (task.isSuccessful) {
                        // Check if the phone number is new or existing
                        val user = auth.currentUser
                        if (user != null) {
                            if (role == "tenant") {
                                // Navigate to Tenant Home Activity
                                context.startActivity(Intent(context, TenantHomeActivity::class.java))
                            } else {
                                // Navigate to Owner Home Activity
                                context.startActivity(Intent(context, OwnerHomeActivity::class.java))
                            }
                        } else {
                            // Navigate to Sign-Up activity if the user is new
                            context.startActivity(Intent(context, SignupActivity::class.java))
                        }
                    } else {
                        // OTP verification failed
                        Toast.makeText(context, "OTP Verification Failed", Toast.LENGTH_SHORT).show()
                    }
                }*/
            },
            modifier = Modifier
                .width(200.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "VERIFY",
                color = Color.White,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Progress Bar (Visible only when progressBarVisibility is true)
        if (progressBarVisibility) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun OTPInput() {
    val otpFields = List(6) { remember { mutableStateOf("") } }

    // Create FocusRequester for each field
    val focusRequesters = List(6) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        otpFields.forEachIndexed { index, otpField ->
            OutlinedTextField(
                value = otpField.value,
                onValueChange = { newValue ->
                    // Ensure the value is a single digit
                    if (newValue.length <= 1) {
                        otpField.value = newValue

                        // Focus the next field when a digit is entered
                        if (newValue.isNotEmpty() && index < otpFields.lastIndex) {
                            focusRequesters[index + 1].requestFocus()
                        } else if (newValue.isEmpty()) {
                            // If the field is cleared (backspace), move focus to previous field
                            if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .size(50.dp)
                    .focusRequester(focusRequesters[index]), // Attach the focus requester
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                maxLines = 1
            )
        }
    }
}

