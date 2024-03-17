package com.amd.mapd721_test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amd.mapd721_test.ui.theme.MAPD721_TestTheme
import com.google.android.gms.analytics.ecommerce.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721_TestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingApp()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ShoppingApp() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        //val to access datastore from ShoppingCart
        val dataStore = ShoppingCart(context)
        // Retrieve the default typography settings
        val typography = MaterialTheme.typography
        // value to access and update through inputs like text fields and dropdown
        var selectedProduct by remember { mutableStateOf("") }
        var selectedProductID by remember { mutableStateOf("") }
        var selectedProductPrice by remember { mutableStateOf("") }
        val showCartDialog = remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Shopping App") },
                    actions = {
                        Button(
                            onClick = {
                                /* Handle cart icon click */
                                scope.launch {
                                    selectedProductID =
                                        dataStore.getProductID.firstOrNull() ?: ""
                                    selectedProduct =
                                        dataStore.getProductName.firstOrNull() ?: ""
                                    selectedProductPrice =
                                        dataStore.getProductPrice.firstOrNull() ?: ""
                                }
                            }
                        ) {
                            Text(text = "View Cart")
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    // Clear the cart when the "Clear" button is clicked
                                    //dataStore.clearCart()
                                }
                                /* Handle Clear button click */
                            }
                        ) {
                            Text(text = "Clear")
                        }
                    }
                )
            }
        ) {
            ProductList(
                selectedProductID,
                selectedProduct,
                selectedProductPrice,
                dataStore,
                scope
            )
        }
    }

    @Composable
    fun ProductList(
        selectedProductID: String,
        selectedProduct: String,
        selectedProductPrice: String,
        dataStore: ShoppingCart,
        scope: CoroutineScope,
    ) {
        val productList = remember {
            listOf(
                Product(1, "Apple", "$10.99"),
                Product(2, "Bananas", "$19.99"),
                Product(3, "Strawberries", "$5.99"),
                Product(4, "Pomegranate", "$4.99"),
                Product(5, "Clementines", "$5.99"),
                Product(6, "Grapes", "$4.59"),
                Product(7, "Lemons", "$2.59"),
                Product(8, "Apple", "$10.99"),
            )
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(productList) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = product.name)
                        Text(text = product.price)
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                dataStore.saveCart(
                                    selectedProductID,
                                    selectedProduct,
                                    selectedProductPrice,
                                )
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(text = "Add to Cart")
                    }
                }
            }
        }
        if (showCartDialog.value) {
            AlertDialog(
                onDismissRequest = { showCartDialog.value = false },
                title = { Text(text = "Cart") },
                text = {
                    Column {
                        // Iterate over the products in the cart and display them
                        val products = runBlocking { dataStore.getAllProducts() }
                        products.forEach { product ->
                            Text(text = "Product ID: ${product.id}")
                            Text(text = "Product Name: ${product.name}")
                            Text(text = "Product Price: ${product.price}")
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showCartDialog.value = false }) {
                        Text(text = "Close")
                    }
                }
            )
        }
    }

    @Composable
    fun Icon(imageVector: ImageVector,contentDescription: String) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
        )
    }

    @Composable
    fun Button(onClick:() -> Unit, text: String) {
        Button(onClick = onClick) {
            Text(text = text)
        }
    }

    @Composable
    fun ListItem(
        text: @Composable () -> Unit,
        secondaryText: @Composable () -> Unit,
        icon: @Composable () -> Unit,
    ) {
        // Customize your list item layout here
        // You can use Card, Row, Column, etc.
        // Example:
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                text()
                secondaryText()
            }
        }
    }
}


