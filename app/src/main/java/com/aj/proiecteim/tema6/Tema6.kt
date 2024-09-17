package com.aj.proiecteim.tema6

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reply.ui.theme.MaterialThemeEIM

class Tema6 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        this.actionBar?.hide()

        super.onCreate(savedInstanceState)

        setContent {
            MaterialThemeEIM {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextFields(context = this)
                }
            }
        }
    }
}

@Composable
fun TextFields(context: ComponentActivity) {
    var stringInput by remember { mutableStateOf("") }
    var numberInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = stringInput,
            onValueChange = { stringInput = it },
            label = { Text("Șir de caractere") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        OutlinedTextField(
            value = numberInput,
            onValueChange = { numberInput = it },
            label = { Text("Număr") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (stringInput.isEmpty() || numberInput.isEmpty())
                    Toast.makeText(context, "Introduceți valori valide", Toast.LENGTH_SHORT).show()
                else {
                    val intent = Intent(context, SecondActivity::class.java).apply {
                        putExtra("stringInput", stringInput)
                        putExtra("numberInput", numberInput)
                    }
                    context.startActivity(intent)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Transmitere date",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
