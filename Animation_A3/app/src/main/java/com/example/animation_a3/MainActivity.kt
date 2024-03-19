@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.animation_a3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import com.example.animation_a3.ui.theme.Animation_A3Theme
import com.example.animation_a3.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Animation_A3Theme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable(
                        "details/{animationType}",
                        deepLinks = listOf(navDeepLink { uriPattern = "details/{animationType}" })
                    ) { backStackEntry ->
                        val animationType = backStackEntry.arguments?.getString("animationType") ?: ""
                        DetailsScreen(navController, animationType)
                    }
                }
            }
        }
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
            navController.navigate("details/$animationType") {
                launchSingleTop = true
                navOptions {
                    anim {
                        enter = android.R.anim.fade_in
                        exit = android.R.anim.fade_out
                        popEnter = android.R.anim.fade_in
                        popExit = android.R.anim.fade_out
                    }
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
fun DetailsScreen(navController: NavController, animationType: String) {
    var isPhotoVisible by remember { mutableStateOf(false) }
    var photoScale by remember { mutableStateOf(1f) }
    val buttonLabel = if (isPhotoVisible) "Press for Exit Animation" else "Press for Enter Animation"

    val photoAlpha: Float by animateFloatAsState(
        targetValue = if (isPhotoVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val photoTranslationX: Float by animateFloatAsState(
        targetValue = if (isPhotoVisible) 0f else -2000f,
        animationSpec = tween(durationMillis = 500)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = getAppBarTitle(animationType))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }

            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.rog),
                    contentDescription = "Photo",
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer(
                            scaleX = photoScale,
                            alpha = photoAlpha,
                            translationX = photoTranslationX
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

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    Animation_A3Theme {
        val context = androidx.compose.ui.platform.LocalContext.current
        DetailsScreen(NavController(context), "transition")
    }
}