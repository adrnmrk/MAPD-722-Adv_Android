package com.amd.adriand_exercise2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amd.adriand_exercise2.ui.theme.AdrianD_Exercise2Theme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdrianD_Exercise2Theme {
                //'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenSetup(DemoViewModel())
                }
            }
        }
    }
}

@JvmOverloads
@Composable
//screensetup from MainScreen fun
fun ScreenSetup(viewModel: DemoViewModel = viewModel()) {
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.result,
        convertTemp = { viewModel.convertTemp(it) },
        switchChange = { viewModel.switchChange() }
    )
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit,
) {
    // Main screen layout with UI
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var textState by remember { mutableStateOf("") }
        // Callback for text input change
        val onTextChange = { text: String ->
            textState = text
        }
        Text(
            "Temperature Converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        //input and unit selection
        InputRow(
            isFahrenheit = isFahrenheit,
            textState = textState,
            switchChange = switchChange,
            onTextChange = onTextChange
        )
        //conversion results
        Text(
            result,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        //button to convert the temp
        Button(
            onClick = { convertTemp(textState) }
        )
        {
            Text("Convert Temperature")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview(model: DemoViewModel = viewModel()) {
    AdrianD_Exercise2Theme {
        MainScreen(
            isFahrenheit = model.isFahrenheit,
            result = model.result,
            convertTemp = { model.convertTemp(it) },
            switchChange = { model.switchChange() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit,
) {
    //Row to show Switch and OutlinedTextField components
    Row(verticalAlignment = Alignment.CenterVertically) {
        //switch F to C units
        Switch(
            checked = isFahrenheit,
            onCheckedChange = { switchChange() }
        )
        //input box
        OutlinedTextField(
            value = textState,
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            label = { Text("Enter Temperature") },
            modifier = Modifier.padding(10.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_thermostat_24),
                    contentDescription = "thermostat",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )
        //Display F or C based on switch status
        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(1000)
        ) { visible ->
            when (visible) {
                true -> Text("\u2109", style = MaterialTheme.typography.headlineMedium)
                false -> Text("\u2103", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}
