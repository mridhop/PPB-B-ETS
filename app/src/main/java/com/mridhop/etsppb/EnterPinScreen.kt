package com.mridhop.etsppb

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun EnterPinScreen(navController: NavController) {
    fun handleKeyPress(key: String, pin: MutableState<String>) {
        when (key) {
            "\u232B" -> if (pin.value.isNotEmpty()) pin.value = pin.value.dropLast(1)
            "\u23CE" -> {
                if (pin.value.length == 6) {
                    navController.navigate("transaction_success_screen")
                }
            }
            else -> if (pin.value.length < 6) pin.value += key
        }
    }

    val pin = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Masukkan PIN Anda",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 80.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(6) {
                Text(
                    text = "\u2022",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (pin.value.length > it) Color.Black else Color.Gray
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {


            Spacer(modifier = Modifier.height(20.dp))

            val numbers = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("\u232B", "0", "\u23CE")
            )

            numbers.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    row.forEach { key ->
                        Text(
                            text = key,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(
                                    horizontal = 40.dp,
                                    vertical = 12.dp
                                )
                                .clickable(
                                    onClick = { handleKeyPress(key, pin) }
                                )
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun EnterPinScreenPreview() {
    EnterPinScreen(rememberNavController())
}