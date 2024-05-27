package com.mridhop.etsppb

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Image(
            painter = painterResource(id = R.drawable.mobile_banking_logo),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.size(60.dp))

        Text(text = "Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Enter your email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.size(28.dp))

        Text(text = "Password")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Enter your password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.size(40.dp))

        Button(
            onClick = { verifyLogin(navController, context, email, password) },
            modifier = Modifier.width(240.dp)
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = { registerUser(context, email, password) },
            modifier = Modifier.width(240.dp)
        ) {
            Text(text = "Register")
        }
    }
}

private fun verifyLogin(
    navController: NavController,
    context: Context,
    email: String,
    password: String
) {
    val sharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
    val storedEmail = sharedPreferences.getString("email", null)
    val storedPassword = sharedPreferences.getString("password", null)

    if (email == storedEmail && password == storedPassword) {
        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
        navController.navigate("enter_pin_screen")
    } else {
        Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
    }
}

private fun registerUser(context: Context, email: String, password: String) {
    val sharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
        return
    }

    editor.putString("email", email)
    editor.putString("password", password)
    editor.apply()

    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}