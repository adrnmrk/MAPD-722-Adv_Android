package com.amd.mapd721_a1_adriandalipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amd.mapd721_a1_adriandalipe.ui.theme.MAPD721A1AdrianDalipeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A1AdrianDalipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    // datastore user details
    val dataStore = StoreUserDetails(context)

    // get saved user details
    val savedUsernameState = dataStore.getUsername.collectAsState(initial = "")
    val savedEmailState = dataStore.getEmail.collectAsState(initial = "")
    val savedIDState = dataStore.getID.collectAsState(initial = "")

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = "Username",
            color = androidx.compose.ui.graphics.Color.Gray,
            fontSize = 12.sp
        )
        //email
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
        )
        // Email Field
        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = "Email",
            color = androidx.compose.ui.graphics.Color.Gray,
            fontSize = 12.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
        )

        // UserId Field
        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = "User ID",
            color = androidx.compose.ui.graphics.Color.Gray,
            fontSize = 12.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = userId,
            onValueChange = { userId = it },
        )
        Spacer(modifier = Modifier.height(20.dp))

        // save button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Save Button


            // Load Button
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 8.dp, end = 8.dp),

                onClick = {
                    // Your action for the Load button

                    email = savedEmailState.value.orEmpty()
                    username = savedUsernameState.value.orEmpty()
                    userId = savedIDState.value.orEmpty()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_yellow)),
                border = BorderStroke(
                    width = 1.0.dp,
                    color = androidx.compose.ui.graphics.Color.Yellow
                ),
            ) {
                Text(
                    text = "Load",
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontSize = 18.sp
                )
            }
            //save button
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 8.dp, end = 8.dp),
                onClick = {

                    scope.launch {
                        dataStore.saveDetails(username, email, userId)
                        email = ""
                        username = ""
                        userId = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)),
                border = BorderStroke(
                    width = 1.0.dp,
                    color = androidx.compose.ui.graphics.Color.Green,
                ),

                ) {
                Text(
                    text = "Save",
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontSize = 18.sp
                )
            }

            // Clear Button
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 8.dp, end = 8.dp),
                onClick = {
                    // Your action for the Clear button
                    scope.launch {
                        email = ""
                        username = ""
                        userId = ""
                        dataStore.clearDetails()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_red)),
                border = BorderStroke(
                    width = 1.0.dp,
                    color = androidx.compose.ui.graphics.Color.Red
                ),
            ) {
                Text(
                    text = "Clear",
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontSize = 18.sp
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()

}