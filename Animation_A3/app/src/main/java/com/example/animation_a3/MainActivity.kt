@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.animation_a3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animation_a3.ui.theme.Animation_A3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Animation_A3Theme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable("transition") {
                        TransitionAnimationScreen(navController) }
                    composable("scale") { ScaleAnimationScreen(navController) }
                    composable("infinite") { InfiniteAnimationScreen(navController) }


                    composable("enter_exit") { EnterExitAnimationScreen(navController) }

                    }
                }
            }
        }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ScaleAnimationScreen(navController: NavHostController) {
        var isScaled by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(
            targetValue = if (isScaled) 2.0f else 1f,
            animationSpec = tween(durationMillis = 500)
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Scale Animation") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp) // Set a fixed size for the scaled area
                            .scale(scale)
                    ) {
                        Button(
                            onClick = {
                                isScaled = !isScaled
                            },
                            modifier = Modifier
                                //.fillMaxSize()
                                .animateContentSize() // Animate the content size change
                        ) {
                            Text(
                                text = "Click Me to Animate",
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }
                }
            }
        )
    }




    private fun InfiniteAnimationScreen(navController: NavHostController) {
    TODO()
    }
}

    @Composable
    fun HomeScreen(navController: NavController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedButton(navController, "Transition Animation", "transition")
            AnimatedButton(navController, "Scale Animation", "scale")
            AnimatedButton(navController, "Infinite Animation", "infinite")
            AnimatedButton(navController, "Enter Exit Animation", "enter_exit")
        }
    }

    @Composable
    fun AnimatedButton(navController: NavController, label: String, animationType: String) {
        Button(
            onClick = {
                when (animationType) {
                    "transition" -> {
                        navController.navigate("transition")
                    }
                    "scale" -> {
                        // Implement Scale Animation screen navigation here
                        navController.navigate("scale")

                    }
                    "infinite" -> {
                        // Implement Infinite Animation screen navigation here
                        navController.navigate("infinite")

                    }
                    "enter_exit" -> {
                        // Implement Enter Exit Animation screen navigation here
                        navController.navigate("enter_exit")
                    }
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = label)
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun TransitionAnimationScreen(navController: NavController) {
        var isRocketLaunched by remember { mutableStateOf(false) }
        val buttonLabel = if (isRocketLaunched) "Land Rocket" else "Launch Rocket"

        // Define the animation properties, start and end position of the image
        val animatedRocketTopOffset by animateFloatAsState(
            targetValue = if (isRocketLaunched)  100f else 500f, // Adjust the targetValue as needed
            animationSpec = tween(durationMillis = 1500) // Adjust the duration as needed
        )
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Transition Animation") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            content = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Image with animated position
                    Image(
                        painter = painterResource(R.drawable.rocket),
                        contentDescription = "Rocket",
                        modifier = Modifier
                            .size(200.dp)
                            .offset(
                                x = (150).dp, // Adjust horizontal offset as needed
                                y = maxOf(animatedRocketTopOffset.dp, 0.dp)
                            )
                    )

                    // Button at the left side of the screen
                    Button(
                        onClick = {
                            isRocketLaunched = !isRocketLaunched
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart) // Align to bottom-left corner
                            .padding(16.dp)
                    ) {
                        Text(text = buttonLabel)
                    }
                }
            }
        )
    }


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EnterExitAnimationScreen(navController: NavController) {
    var isPhotoVisible by remember { mutableStateOf(false) }
    var photoScale by remember { mutableStateOf(1f) }
    val buttonLabel =
        if (isPhotoVisible) "Press for Exit Animation" else "Press for Enter Animation"

    val photoTranslationX: Float by animateFloatAsState(
        targetValue = if (isPhotoVisible) 125f else -5000f, // Adjust targetValue as needed
        animationSpec = tween(durationMillis = 1500) // Adjust duration as needed
    )

    val photoTranslationY: Float by animateFloatAsState(
        targetValue = if (isPhotoVisible) 0f else -500f, // Adjust targetValue as needed
        animationSpec = tween(durationMillis = 1500) // Adjust duration as needed
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Enter Exit Animation") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(R.drawable.rog),
                    contentDescription = "Photo",
                    modifier = Modifier
                        .size(300.dp)
                        .graphicsLayer(
                            translationX = photoTranslationX,
                            translationY = photoTranslationY
                        )
                )

                Button(
                    onClick = {
                        isPhotoVisible = !isPhotoVisible
                        photoScale = if (isPhotoVisible) 1f else 0.5f
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = buttonLabel)
                }
            }
        }
    )
}




@Composable
    fun getAppBarTitle(animationType: String): String {
        return when (animationType) {
            "transition" -> "Transition Animation"
            "scale" -> "Scale Animation"
            "infinite" -> "Infinite Animation"
            "enter_exit" -> "Enter Exit Animation"
            else -> "Details"
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HomeScreenPreview() {
        Animation_A3Theme {
            val context = androidx.compose.ui.platform.LocalContext.current
            HomeScreen(NavController(context))
        }
    }

