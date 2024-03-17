package com.amd.midtermpractice

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amd.midtermpractice.ui.theme.MidtermPracticeTheme
import kotlinx.coroutines.flow.firstOrNull
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

    //val to access datastore from StoreWeatherDetails
    val dataStore = StoreWeatherDetails(context)
    // Retrieve the default typography settings
    val typography = MaterialTheme.typography
   // value to access and update through inputs like text fields and dropdown
    var selectedCity by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    //temp scale checkbox
    var temperatureScale by remember { mutableStateOf("C") }

    var wind by remember { mutableStateOf("") }
    //windspeed radio
    var windSpeed by remember { mutableStateOf("KPH")

    }

    var description by remember { mutableStateOf("") }
    //list of cities
    val cities = listOf("Toronto", "Montreal", "Vancouver")
    //column for the entire view
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Spinner for selecting city
        var expanded by remember { mutableStateOf(false) }

        // start DropdownMenu for selecting city
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(onClick = { expanded = true })
        ) {
                if (selectedCity.isEmpty()) {
                // Show the label "City" if no city is selected
                Text(
                    text = "City",
                    style = typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { expanded = true })
                        .padding(vertical = 8.dp)
                )
            } else {
            // Show the selected city text field if a city is selected
            Text(
                text = selectedCity,
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { expanded = true })
                    .padding(vertical = 8.dp)
            )
        }
            //dropdown or spinner for city
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)

            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = {
                        selectedCity = city
                        expanded = false
                    })
                }
            }
        }//end dropdown
// Save the selected city name when it's changed
        DisposableEffect(selectedCity) {
            scope.launch {
                dataStore.saveCityName(selectedCity)
            }
            onDispose {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Text fields for weather details
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date DD-MM-YYYY") },
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
        //begin checkbox
        Checkbox(
            checked = temperatureScale == "F",
            onCheckedChange = {temperatureScale = if (it) "F" else "C"})
        Text(text = "Temperature in Fahrenheit?", modifier = Modifier.padding(start = 2.dp))

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = wind,
            onValueChange = { wind = it },
            label = { Text("Wind") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        //Radio buttons for wind
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            RadioButton(
                selected = windSpeed == "KPH",
                onClick = { windSpeed = "KPH" }
            )
            Text(text = "KPH", modifier = Modifier.padding(start = 4.dp))

            RadioButton(
                selected = windSpeed == "MPH",
                onClick = { windSpeed = "MPH" },
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(text = "MPH", modifier = Modifier.padding(start = 4.dp))
        }//radio end


        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        //save weather data button
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
            //retrieve weather details and display on fields from datastore

            Button(
                onClick = {
                    scope.launch {
                        val cityName = selectedCity
                        val savedCityName = dataStore.getCityName.firstOrNull()
                        if (savedCityName != null && cityName.isNotEmpty() && savedCityName == cityName) {
                            date = dataStore.getDate.firstOrNull() ?: ""
                            temperature = dataStore.getTemp.firstOrNull() ?: ""
                            wind = dataStore.getWind.firstOrNull() ?: ""
                            description = dataStore.getDescription.firstOrNull() ?: ""
                            showToast(context, "Weather details loaded.")
                        } else {
                            showToast(context, "No weather details found for the selected city.")
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Show Weather")
            }

        }
        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Text fields for displaying weather details
        Text(text = "Date: $date", fontSize = 16.sp)
        Text(text = "Temperature: $temperature $temperatureScale", fontSize = 16.sp)
        Text(text = "Wind: $wind $windSpeed", fontSize = 16.sp)
        Text(text = "Description: $description", fontSize = 16.sp)

    }

}
// Define an enum for wind units


// Helper function to show a Toast message
private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherApp()
}