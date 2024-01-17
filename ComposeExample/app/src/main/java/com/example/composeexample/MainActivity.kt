package com.example.composeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeexample.ui.theme.ComposeExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                // A surface container using the 'background' color from the theme
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeExampleTheme {

        var countnum by remember { mutableStateOf(0)}
        var taskname by remember { mutableStateOf(" ")}
        var tasks by remember {
            mutableStateOf(listOf<String>())
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
        {
            Row(modifier = Modifier.fillMaxWidth())
            {
                OutlinedTextField(value = taskname, onValueChange ={text ->
                    taskname= text
                } )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if(taskname.isNotBlank()){

                        tasks = tasks + taskname
                        taskname=""
                    }
                }) {
                    Text(text = "Add")
                }
            }
            LazyColumn {
                items(tasks){
                        currenttask ->
                        Text(text = currenttask,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp))
                        Divider()

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeExampleTheme {
        Greeting(name = "MAPD 721")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

        LazyColumn(modifier.fillMaxSize()){
            items(10) {
                index ->
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Account",modifier=Modifier.size(100.dp))

            }
        }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeExampleTheme {
        var countnum by remember { mutableStateOf(0)}

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = countnum.toString())
            Text(text = "Times")

            Button(onClick = { countnum++}) {
                Text(text = "Click Me: $countnum")

            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column (modifier.size(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Text(
            text = "Hello $name!",
            color= Color.Green,
            fontSize= 20.sp,


            )
        Text(
            text = "How",
            color= Color.Blue,
            fontSize= 20.sp,

            )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box (modifier.size(400.dp),
        contentAlignment = Alignment.Center

    ){
        Text(
            text = "Hello $name!",
            color= Color.Green,
            fontSize= 20.sp,
            modifier=Modifier.align(Alignment.BottomEnd)


        )
        Text(
            text = "How",
            color= Color.Blue,
            fontSize= 20.sp,

            )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    if(name.length>5) {
        Column() {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "hello",
                modifier.background(Color.Blue)
            )
            for(i in 1..10){

                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Account")
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(text = "Hello $name!")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var inputvalue by remember { mutableStateOf("") }

    Column (modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Hello $name!",)

                        TextField(value = inputvalue,
                            onValueChange ={text -> inputvalue= text },
                            placeholder = { Text(text = "Enter user name") },
                        )

        Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if(inputvalue.isNotBlank()){


                        inputvalue=""
                    }
                }) {
                    Text(text = "Add")
                }
        }
}
