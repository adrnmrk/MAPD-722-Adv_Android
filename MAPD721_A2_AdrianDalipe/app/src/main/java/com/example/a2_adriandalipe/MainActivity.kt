@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.a2_adriandalipe

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2_adriandalipe.ui.theme.A2_AdrianDalipeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A2_AdrianDalipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactsList(context = this)
                }
            }
        }
    }
}

@Composable
fun ContactsList(context: ComponentActivity) {
    var contactName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var contacts by remember { mutableStateOf(loadContacts(context)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Contact Name TextField
        TextField(
            value = contactName,
            onValueChange = { contactName = it },
            label = { Text("Contact Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Contact Number TextField
        TextField(
            value = contactNumber,
            onValueChange = { contactNumber = it },
            label = { Text("Contact Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Save Button
        Button(
            onClick = {
                saveContact(context, contactName, contactNumber)
                // Refresh the contact list after saving
                contacts = loadContacts(context)
                // Clear text fields
                contactName = ""
                contactNumber = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Save Contact")
        }

        // Load Button
        Button(
            onClick = {
                // Load all contacts
                contacts = loadContacts(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Load Contacts")
        }

        // Contact List
        if (contacts.isEmpty()) {
            Text(text = "No contacts available")
        } else {
            LazyColumn {
                items(contacts) { contact ->
                    ContactItem(contact)
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Person, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = "Name: ${contact.displayName}")
            Text(text = "Phone: ${contact.contactNumber}")
        }
    }
}

data class Contact(val displayName: String, val contactNumber: String)

@SuppressLint("Range")
fun loadContacts(context: ComponentActivity): List<Contact> {
    val contacts = mutableListOf<Contact>()

    context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null,
        null,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            do {
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contacts.add(Contact(displayName, phoneNumber))
            } while (cursor.moveToNext())
        }
    }

    return contacts
}

fun saveContact(context: ComponentActivity, name: String, number: String) {
    val values = ContentValues().apply {
        put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, name)
        put(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
        put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
        )
    }

    context.contentResolver.insert(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        values
    )
}

@Preview(showBackground = true)
@Composable
fun ContactsListPreview() {
    A2_AdrianDalipeTheme {
        ContactsList(context = ComponentActivity())
    }
}