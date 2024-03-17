package com.amd.midtermpractice

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class StoreWeatherDetails(private val context: Context) {
    companion object {
        //  DataStore instance named UserDetails
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("WeatherDetails")

        //key-value pairs to identify user details
        val CITY_NAME_KEY = stringPreferencesKey("city_name")
        val DATE_KEY= stringPreferencesKey("date")
        val TEMP_KEY = stringPreferencesKey("temp")
        val WIND_KEY = stringPreferencesKey("wind")
        val DESCRIPTION_KEY = stringPreferencesKey("description")
    }

    // Flow reps con't stream of data, this retrieves values from preferences data
    val getCityName: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[CITY_NAME_KEY] ?: ""
        }
    val getDate: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[DATE_KEY] ?: ""
        }
    val getTemp: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[TEMP_KEY] ?: ""
        }
    val getWind: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[WIND_KEY] ?: ""
        }
    val getDescription: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[DESCRIPTION_KEY] ?: ""
        }

    // save user details in DataStore
    suspend fun saveWeatherDetails(city: String, date: String, temp: String, wind: String, description: String) {
        // Use the DataStore's edit function to make changes to the stored preferences
        context.datastore.edit { preferences ->
            // Update the values in the preferences
            preferences[CITY_NAME_KEY] = city
            preferences[DATE_KEY] = date.toString()
            preferences[TEMP_KEY] = temp.toString()
            preferences[WIND_KEY] = wind.toString()
            preferences[DESCRIPTION_KEY] = description

        }
        // Show a Toast message
        showToast("Weather details saved successfully.")
    }

    // Helper function to show a Toast message
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    
    // Function to save the selected city name in DataStore
    suspend fun saveCityName(city: String) {
        context.datastore.edit { preferences ->
            preferences[CITY_NAME_KEY] = city
        }
    }

}