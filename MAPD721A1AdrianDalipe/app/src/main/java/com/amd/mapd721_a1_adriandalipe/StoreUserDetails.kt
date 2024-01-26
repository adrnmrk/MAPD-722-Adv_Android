package com.amd.mapd721_a1_adriandalipe

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import java.util.prefs.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserDetails (private val context: Context) {
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserDetails")

        //keys to id user details in datastore
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    val getUsername: Flow<String?> = context.dataStore.data
        .map {preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }
    val getEmail: Flow<String?> = context.dataStore.data
        .map {preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }
    val getID: Flow<String?> = context.dataStore.data
        .map {preferences ->
            preferences[USER_ID_KEY] ?: ""
        }
    // Function to save user email and password in DataStore
    suspend fun saveDetails(username: String, email: String, id: String) {
        // Use the DataStore's edit function to make changes to the stored preferences
        context.dataStore.edit { preferences ->
            // Update the values in the preferences

            preferences[USER_NAME_KEY] = username
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = id

        }
    }


}



