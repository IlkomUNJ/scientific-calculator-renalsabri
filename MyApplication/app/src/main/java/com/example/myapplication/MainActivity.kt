package com.example.myapplication

import android.os.Bundle
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun ButtonGrid(modifier: Modifier = Modifier) {
    var isScientificMode by remember { mutableStateOf(true) }
    var displayText by remember { mutableStateOf("0") }
    var operationDisplay by remember { mutableStateOf("") }
    var currentInput by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var previousValue by remember { mutableStateOf(0.0) }
    var shouldResetDisplay by remember { mutableStateOf(false) }

    fun calculateResult(): Double {
        val currentValue = currentInput.toDoubleOrNull() ?: 0.0
        return when (operator) {
            "+" -> previousValue + currentValue
            "-" -> previousValue - currentValue
            "x" -> previousValue * currentValue
            "/" -> if (currentValue != 0.0) previousValue / currentValue else 0.0
            "%" -> previousValue % currentValue
            "^" -> previousValue.pow(currentValue)
            else -> currentValue
        }
    }

    fun handleButtonClick(buttonText: String) {
        when (buttonText) {
            "C" -> {
                displayText = "0"
                operationDisplay = ""
                currentInput = ""
                operator = ""
                previousValue = 0.0
                shouldResetDisplay = false
            }
            "Del" -> {
                if (currentInput.isNotEmpty()) {
                    currentInput = currentInput.dropLast(1)
                    displayText = if (currentInput.isEmpty()) "0" else currentInput
                }
            }
            in listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9") -> {
                if (shouldResetDisplay) {
                    currentInput = buttonText
                    shouldResetDisplay = false
                } else {
                    currentInput = if (currentInput == "0" || currentInput.isEmpty()) buttonText else currentInput + buttonText
                }
                displayText = currentInput
            }
            "." -> {
                if (!currentInput.contains(".")) {
                    currentInput = if (currentInput.isEmpty()) "0." else currentInput + "."
                    displayText = currentInput
                }
            }
            in listOf("+", "-", "x", "/", "%", "^") -> {
                if (currentInput.isNotEmpty()) {
                    if (operator.isNotEmpty()) {
                        val result = calculateResult()
                        displayText = result.toString()
                        previousValue = result
                        operationDisplay = "$result $buttonText"
                    } else {
                        previousValue = currentInput.toDoubleOrNull() ?: 0.0
                        operationDisplay = "$previousValue $buttonText"
                    }
                    operator = buttonText
                    currentInput = ""
                    shouldResetDisplay = false
                }
            }
            "=" -> {
                if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
                    val result = calculateResult()
                    operationDisplay = "$previousValue $operator $currentInput ="
                    displayText = result.toString()
                    currentInput = result.toString()
                    operator = ""
                    previousValue = 0.0
                    shouldResetDisplay = true
                }
            }
            "√" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                val result = sqrt(value)
                operationDisplay = "√($value)"
                displayText = result.toString()
                currentInput = result.toString()
                shouldResetDisplay = true
            }
            "±" -> {
                if (currentInput.isNotEmpty() && currentInput != "0") {
                    currentInput = if (currentInput.startsWith("-")) {
                        currentInput.drop(1)
                    } else {
                        "-$currentInput"
                    }
                    displayText = currentInput
                }
            }
            "x²" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                val result = value.pow(2)
                operationDisplay = "($value)²"
                displayText = result.toString()
                currentInput = result.toString()
                shouldResetDisplay = true
            }
            "1/x" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                if (value != 0.0) {
                    val result = 1.0 / value
                    operationDisplay = "1/($value)"
                    displayText = result.toString()
                    currentInput = result.toString()
                    shouldResetDisplay = true
                }
            }
            "sin" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                val result = sin(Math.toRadians(value))
                operationDisplay = "sin($value°)"
                displayText = result.toString()
                currentInput = result.toString()
                shouldResetDisplay = true
            }
            "cos" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                val result = cos(Math.toRadians(value))
                operationDisplay = "cos($value°)"
                displayText = result.toString()
                currentInput = result.toString()
                shouldResetDisplay = true
            }
            "tan" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                val result = tan(Math.toRadians(value))
                operationDisplay = "tan($value°)"
                displayText = result.toString()
                currentInput = result.toString()
                shouldResetDisplay = true
            }
            "log" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                if (value > 0) {
                    val result = log10(value)
                    operationDisplay = "log($value)"
                    displayText = result.toString()
                    currentInput = result.toString()
                    shouldResetDisplay = true
                }
            }
            "ln" -> {
                val value = currentInput.toDoubleOrNull() ?: 0.0
                if (value > 0) {
                    val result = ln(value)
                    operationDisplay = "ln($value)"
                    displayText = result.toString()
                    currentInput = result.toString()
                    shouldResetDisplay = true
                }
            }
            "π" -> {
                currentInput = PI.toString()
                displayText = currentInput
                operationDisplay = "π"
                shouldResetDisplay = true
            }
            "xʸ" -> {
                if (currentInput.isNotEmpty()) {
                    previousValue = currentInput.toDoubleOrNull() ?: 0.0
                    operator = "^"
                    operationDisplay = "$previousValue ^"
                    currentInput = ""
                    shouldResetDisplay = false
                }
            }
            "!" -> {
                val value = currentInput.toDoubleOrNull()?.toInt() ?: 0
                if (value >= 0) {
                    var result = 1.0
                    for (i in 2..value) {
                        result *= i
                    }
                    operationDisplay = "$value!"
                    displayText = result.toString()
                    currentInput = result.toString()
                    shouldResetDisplay = true
                }
            }
            "(" -> {
                currentInput += "("
                displayText = currentInput
            }
            ")" -> {
                currentInput += ")"
                displayText = currentInput
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = operationDisplay,
                fontSize = 20.sp,
                textAlign = TextAlign.End,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
        }
        /*main display*/
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Transparent)
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = displayText,
                fontSize = 48.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold
            )
        }

        val rowCount = if (isScientificMode) 7 else 5

        for (row in 0 until rowCount) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
            ) {
                val colCount = if (isScientificMode) 5 else 4

                for (col in 0 until colCount) {
                    val buttonNumber = row * 4 + col + 1

                    val buttonText = if (isScientificMode) {
                        when {
                            row == 0 && col == 0 -> "C"
                            row == 0 && col == 1 -> "Del"
                            row == 0 && col == 2 -> "%"
                            row == 0 && col == 3 -> "/"
                            row == 0 && col == 4 -> "^"
                            row == 1 && col == 0 -> "7"
                            row == 1 && col == 1 -> "8"
                            row == 1 && col == 2 -> "9"
                            row == 1 && col == 3 -> "x"
                            row == 1 && col == 4 -> "("
                            row == 2 && col == 0 -> "4"
                            row == 2 && col == 1 -> "5"
                            row == 2 && col == 2 -> "6"
                            row == 2 && col == 3 -> "-"
                            row == 2 && col == 4 -> ")"
                            row == 3 && col == 0 -> "1"
                            row == 3 && col == 1 -> "2"
                            row == 3 && col == 2 -> "3"
                            row == 3 && col == 3 -> "+"
                            row == 3 && col == 4 -> "√"
                            row == 4 && col == 0 -> "M"
                            row == 4 && col == 1 -> "0"
                            row == 4 && col == 2 -> "."
                            row == 4 && col == 3 -> "="
                            row == 4 && col == 4 -> "±"
                            row == 5 && col == 0 -> "sin"
                            row == 5 && col == 1 -> "cos"
                            row == 5 && col == 2 -> "tan"
                            row == 5 && col == 3 -> "log"
                            row == 5 && col == 4 -> "ln"
                            row == 6 && col == 0 -> "π"
                            row == 6 && col == 1 -> "xʸ"
                            row == 6 && col == 2 -> "x²"
                            row == 6 && col == 3 -> "1/x"
                            row == 6 && col == 4 -> "!"
                            else -> ""
                        }
                    } else {
                        // Simple layout
                        when {
                            row == 0 && col == 0 -> "C"
                            row == 0 && col == 1 -> "%"
                            row == 0 && col == 2 -> "/"
                            row == 0 && col == 3 -> "Del"
                            row == 1 && col == 0 -> "7"
                            row == 1 && col == 1 -> "8"
                            row == 1 && col == 2 -> "9"
                            row == 1 && col == 3 -> "x"
                            row == 2 && col == 0 -> "4"
                            row == 2 && col == 1 -> "5"
                            row == 2 && col == 2 -> "6"
                            row == 2 && col == 3 -> "-"
                            row == 3 && col == 0 -> "1"
                            row == 3 && col == 1 -> "2"
                            row == 3 && col == 2 -> "3"
                            row == 3 && col == 3 -> "+"
                            row == 4 && col == 0 -> "M"
                            row == 4 && col == 1 -> "0"
                            row == 4 && col == 2 -> "."
                            row == 4 && col == 3 -> "="
                            else -> ""
                        }
                    }

                    val isNumber = buttonText in listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

                    val isOperator = buttonText in listOf("/", "x", "-", "+", "=", "%", "^", "√", "±", "(", ")", "sin", "cos", "tan", "log", "ln", "π", "xʸ", "x²", "1/x", "!")

                    val backgroundColor = when {
                        isNumber -> Color(0xFF87CEEB)
                        isOperator -> Color(0xFFFF8C00)
                        else -> Color.Gray
                    }

                    val textColor = when {
                        isNumber -> Color.Black
                        isOperator -> Color.White
                        else -> Color.White
                    }

                    Button(
                        onClick = {
                            if (buttonText == "M") {
                                isScientificMode = !isScientificMode
                            } else {
                                handleButtonClick(buttonText)
                            }
                        },
                        modifier = Modifier.size(70.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor,
                            contentColor = textColor
                        )
                    ) {
                        Text(
                            text = buttonText,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        ButtonGrid()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MyApp()
    }
}