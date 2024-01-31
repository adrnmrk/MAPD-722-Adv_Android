package com.example.contentproviderdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.contentproviderdemo.ui.theme.ContentProviderDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentProviderDemoTheme {
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

//    val selection = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} = ?"
//    val selectionArgs = arrayOf("The Professor")


//    val selection = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " IS NOT NULL"
//    val selectionArgs = arrayOf("T%")
    val sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
//
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
        Text(text = contact.displayName)
    }
}

data class Contact(val displayName: String)

@SuppressLint("Range")
@Composable
fun loadContacts(context: ComponentActivity, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): List<Contact> {
    val contacts = mutableListOf<Contact>()

    // Use the content resolver to query contacts

//    select Column1,Column2,Column3 FROM Table WHERE Column1 > 100 ORDER BY Column1, Column2  ASC
//    SELECT Name,Phone FROM Contacts WHERE NAME NOT LIKE 'J%' ORDER BY Name ASC
    context.contentResolver.query(
        //URI - Uniform Resource Identifier
        ContactsContract.Contacts.CONTENT_URI,
        //CalendarContract.Attendees.PROJECTION,
        //MediaStore.Images.Media.DATA,
        //MediaStrore.Audio.Media.ARTIST,
        arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY),
        selection,
        selectionArgs,
        sortOrder


    )?.use { cursor ->
        // Check if the cursor has data
        if (cursor.moveToFirst()) {
            do {
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                contacts.add(Contact(displayName))
            } while (cursor.moveToNext())
        }
    }

    return contacts
}

@Preview(showBackground = true)
@Composable
fun ContactsListPreview() {
    ContentProviderDemoTheme {
        ContactsList(context = ComponentActivity())
    }
}