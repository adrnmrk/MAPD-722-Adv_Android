package com.example.a2_adriandalipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2_adriandalipe.ui.theme.A2_AdrianDalipeTheme

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
    val sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY

    val selection = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE ?"
    val selectionArgs = arrayOf("%")  // The % acts as a wildcard, meaning any characters can follow 'D'
    val contacts by rememberUpdatedState(newValue = loadContacts(context, selection, selectionArgs, sortOrder))

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
@Composable
fun loadContacts(context: ComponentActivity, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): List<Contact> {
    val contacts = mutableListOf<Contact>()

    // Use the content resolver to query contacts

    context.contentResolver.query(
        //URI - Uniform Resource Identifier
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, // Use Phone.CONTENT_URI to query phone numbers
        arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        selection,
        selectionArgs,
        sortOrder
    )?.use { cursor ->
        // Check if the cursor has data
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

@Preview(showBackground = true)
@Composable
fun ContactsListPreview() {
    A2_AdrianDalipeTheme {
        ContactsList(context = ComponentActivity())
    }
}