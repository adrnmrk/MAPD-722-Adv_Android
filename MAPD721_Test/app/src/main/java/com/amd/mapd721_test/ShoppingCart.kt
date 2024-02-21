package com.amd.mapd721_test

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.analytics.ecommerce.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
data class Product(val id: Int, val name: String, val price: String)

class ShoppingCart(private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("ShoppingCart")

        val PRODUCT_ID_KEY = stringPreferencesKey("product_id")
        val PRODUCT_NAME_KEY = stringPreferencesKey("product_name")
        val PRODUCT_PRICE_KEY = stringPreferencesKey("product_price")
    }

    val getProductID: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[PRODUCT_ID_KEY]
        }

    val getProductName: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[PRODUCT_NAME_KEY]
        }

    val getProductPrice: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[PRODUCT_PRICE_KEY]
        }

    suspend fun getAllProducts(): List<Product> {
        val productId = getProductID.firstOrNull() ?: ""
        val productName = getProductName.firstOrNull() ?: ""
        val productPrice = getProductPrice.firstOrNull() ?: ""

        val productIds = productId.split(",").filter { it.isNotBlank() }
        val productNames = productName.split(",").filter { it.isNotBlank() }
        val productPrices = productPrice.split(",").filter { it.isNotBlank() }

        val productList = mutableListOf<Product>()
        for (i in productIds.indices) {
            val id = productIds[i].toIntOrNull() ?: continue
            val name = productNames.getOrNull(i) ?: continue
            val price = productPrices.getOrNull(i) ?: continue
            productList.add(Product(id, name, price))
        }

        return productList
    }

    suspend fun saveCart(productId: String, productName: String, productPrice: String) {
        context.datastore.edit { preferences ->
            preferences[PRODUCT_ID_KEY] = productId
            preferences[PRODUCT_NAME_KEY] = productName
            preferences[PRODUCT_PRICE_KEY] = productPrice
        }

        showToast("Item added to cart")
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}