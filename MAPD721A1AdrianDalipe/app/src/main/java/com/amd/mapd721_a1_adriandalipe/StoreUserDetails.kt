package com.amd.mapd721_a1_adriandalipe

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * store and retrieve user details using DataStore*/
class StoreUserDetails(private val context: Context) {
    companion object {
        //  DataStore instance named UserDetails
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserDetails")

        //key-value pairs to identify user details
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    // Flow reps con't stream of data, this retrieves values from preferences data
    val getUsername: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }
    val getID: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    // function to save user details in DataStore
    suspend fun saveDetails(username: String, email: String, id: String) {
        // Use the DataStore's edit function to make changes to the stored preferences
        context.dataStore.edit { preferences ->
            // Update the values in the preferences
            preferences[USER_NAME_KEY] = username
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = id
        }
        // Show a Toast message
        showToast("User details saved successfully.")
    }

    // Helper function to show a Toast message
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    // function to clear user details in DataStore
    suspend fun clearDetails() {
        // delete details from datastore
        context.dataStore.edit { preferences ->
            // Update the values in the preferences
            preferences.remove(USER_NAME_KEY)
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_ID_KEY)

        }
        // Show a Toast message
        showToast("User details cleared.")
    }


}



