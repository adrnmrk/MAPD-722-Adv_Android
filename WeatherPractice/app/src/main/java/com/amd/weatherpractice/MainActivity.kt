package com.amd.weatherpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            WeatherPracticeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
//    }
//}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherRecordingScreen()
        }
    }
}

data class WeatherInfo(
    val city: String,
    val date: String,
    val temperature: String,
    val wind: String,
    val description: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherRecordingScreen() {
    var city by remember { mutableStateOf("Toronto") }
    var date by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var wind by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var savedWeather by remember { mutableStateOf<WeatherInfo?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        var expanded by remember { mutableStateOf(false) }
        // Dropdown menu for city selection
        DropdownMenu(
            expanded = false, // Set to true when clicked
            onDismissRequest = { expanded = false }
        ) {
            listOf("Toronto", "Calgary", "Montreal", "Vancouver").forEach { cityOption ->
                DropdownMenuItem(onClick = { city = cityOption },
                    text = { Text(text = cityOption) })
            }
        }
        Text("Selected city: $city")

        Spacer(modifier = Modifier.height(16.dp))

        // Text fields for weather info
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            placeholder = { Text(text = "Date") }
        )
        OutlinedTextField(
            value = temperature,
            onValueChange = { temperature = it },
            placeholder = { Text("Temperature") }
        )
        OutlinedTextField(
            value = wind,
            onValueChange = { wind = it },
            placeholder = { Text("Wind") }
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Save and Show buttons
        Row {
            Button(onClick = { /* Save weather info to datastore */ }) {
                Text("Save Weather")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                savedWeather = WeatherInfo(city, date, temperature, wind, description)
            }) {
                Text("Show Weather")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display saved weather info
        savedWeather?.let { weather ->
            Text("City: ${weather.city}")
            Text("Date: ${weather.date}")
            Text("Temperature: ${weather.temperature}")
            Text("Wind: ${weather.wind}")
            Text("Description: ${weather.description}")
        }
    }



}
