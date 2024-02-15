package com.amd.midtermpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amd.midtermpractice.ui.theme.MidtermPracticeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidtermPracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    WeatherApp()
                }
            }
        }
    }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WeatherApp() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val dataStore = StoreWeatherDetails(context)

        var selectedCity by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var temperature by remember { mutableStateOf("") }
        var wind by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }

        val cities = listOf("Toronto", "Montreal", "Vancouver")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "City",
                modifier = Modifier
                    .padding(16.dp, top = 30.dp),
                color = androidx.compose.ui.graphics.Color.Gray,
                fontSize = 12.sp
            )
            // Spinner for selecting city
            OutlinedTextField(
                value = selectedCity,
                onValueChange = { selectedCity = it },
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Text fields for weather details
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = temperature,
                onValueChange = { temperature = it },
                label = { Text("Temperature") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = wind,
                onValueChange = { wind = it },
                label = { Text("Wind") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            dataStore.saveWeatherDetails(
                                selectedCity,
                                date,
                                temperature,
                                wind,
                                description
                            )
                            date = ""
                            temperature = ""
                            wind = ""
                            description = ""
                        }
                    },
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(1.dp, colorResource(id = R.color.purple_200)),
                    colors = ButtonDefaults.buttonColors(
                    )
                ) {
                    Text("Save Weather")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        // Handle showing weather details
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Show Weather")
                }
            }

        }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherApp()
}