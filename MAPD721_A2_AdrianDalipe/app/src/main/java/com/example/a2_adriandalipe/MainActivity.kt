@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.a2_adriandalipe

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.a2_adriandalipe.ui.theme.A2_AdrianDalipeTheme

// manage permission request
const val MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 123
//data class for contact data
data class Contact(val displayName: String, val contactNumber: String)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A2_AdrianDalipeTheme {
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
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_CONTACTS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request the permission
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(Manifest.permission.WRITE_CONTACTS),
                        MY_PERMISSIONS_REQUEST_WRITE_CONTACTS
                    )
                } else {
                    // Permission already granted, call saveContact function
                    saveContact(context, contactName, contactNumber)

                    // Clear the text fields after saving
                    contactName = ""
                    contactNumber = ""

                    // Reload contacts
                    contacts = loadContacts(context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)

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
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue)

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
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(text = "Name: ${contact.displayName}")
            Text(text = "Phone: ${contact.contactNumber}")
        }
    }
}
//query the phone contacts using the content resolver,
// retrieves the display name and phone number from the result cursor, and creates a list of Contact objects.
@SuppressLint("Range")
fun loadContacts(context: ComponentActivity): List<Contact> {
    val contacts = mutableListOf<Contact>()

    // Query content resolver for contact data
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
                // Retrieve name and phone number from the cursor
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

                // Check if phone number is not null
                val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val phoneNumber = if (phoneNumberIndex != -1) cursor.getString(phoneNumberIndex) else null

                // Add contact only if both name and phone number are not null
                if (displayName != null && phoneNumber != null) {
                    contacts.add(Contact(displayName, phoneNumber))
                }
            } while (cursor.moveToNext())
        }
    }

    return contacts
}
//save contact to the contact list
fun saveContact(context: ComponentActivity, name: String, number: String) {
    try {
        // Check if name and number are not empty
        if (name.isNotEmpty() && number.isNotEmpty()) {
            val rawContactId = getRawContactId(context)

            if (rawContactId != null) {
                // Save contact name
                val nameValues = ContentValues().apply {
                    put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                }

                context.contentResolver.insert(
                    ContactsContract.Data.CONTENT_URI,
                    nameValues
                )

                // Save phone number
                val phoneValues = ContentValues().apply {
                    put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Phone.NUMBER, number)

                }

                context.contentResolver.insert(
                    ContactsContract.Data.CONTENT_URI,
                    phoneValues
                )

                // Show a success toast message
                Toast.makeText(context, "Contact saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                // Handle the case where raw_contact_id is not available
                // Show an error toast message
                Toast.makeText(context, "Failed to save contact. Try again later.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Show a toast message for empty fields
            Toast.makeText(context, "Name and phone number cannot be empty.", Toast.LENGTH_SHORT).show()

        }
    } catch (e: Exception) {
        // Log the exception for further debugging
        e.printStackTrace()

        // Show an error toast message
        Toast.makeText(context, "Failed to save contact. Try again later.", Toast.LENGTH_SHORT).show()
    }
}



private fun getRawContactId(context: ComponentActivity): Long? {
    val rawContactUri = context.contentResolver.insert(
        ContactsContract.RawContacts.CONTENT_URI,
        ContentValues()
    )

    return rawContactUri?.let {
        val pathSegments = it.pathSegments
        if (pathSegments.size > 1) {
            return pathSegments[1].toLong()
        }
        null
    }
}


@Preview(showBackground = true)
@Composable
fun ContactsListPreview() {
    A2_AdrianDalipeTheme {
        ContactsList(context = ComponentActivity())
    }
}