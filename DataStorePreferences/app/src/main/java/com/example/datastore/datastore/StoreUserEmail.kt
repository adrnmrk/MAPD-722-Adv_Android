package com.example.datastore.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserEmail(private val context: Context) {

    // Companion object to create a single instance of DataStore for user email and password
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserEmail")

        // Keys to uniquely identify user email and password in DataStore
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_PASSWORD_KEY = stringPreferencesKey("user_password")
    }

    // Flow representing the user's stored email
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            // Retrieve the stored email value or return an empty string if not present
            preferences[USER_EMAIL_KEY] ?: ""
        }

    // Flow representing the user's stored password
    val getPassword: Flow<String?> = context.dataStore.data
        .map { preferences ->
            // Retrieve the stored pas`sword value or return an empty string if not present
            preferences[USER_PASSWORD_KEY] ?: ""
        }

    // Function to save user email and password in DataStore
    suspend fun saveEmail(email: String, password: String) {
        // Use the DataStore's edit function to make changes to the stored preferences
        context.dataStore.edit { preferences ->
            // Update the user email and password values in the preferences
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_PASSWORD_KEY] = password
        }
    }
}
