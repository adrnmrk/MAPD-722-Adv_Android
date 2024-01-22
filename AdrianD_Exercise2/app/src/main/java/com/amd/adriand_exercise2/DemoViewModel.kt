package com.amd.adriand_exercise2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class DemoViewModel : ViewModel() {
    var isFahrenheit by mutableStateOf(true)
    var result by mutableStateOf("")
    //convert temp based on input
    fun convertTemp(temp: String) {
        //try catch for invalid inputs
        result = try {
            val tempInt = temp.toInt()
            if (isFahrenheit) {
                //F to C
                ((tempInt - 32) * 0.5556).roundToInt().toString() + " C"
            } else {
                //C to F
                ((tempInt * 1.8) + 32).roundToInt().toString() + " F"
            }
        } catch (e: Exception) {
            "Invalid Entry"
        }
    }
    //toggle switch
    fun switchChange() {
        isFahrenheit = !isFahrenheit
    }
}

