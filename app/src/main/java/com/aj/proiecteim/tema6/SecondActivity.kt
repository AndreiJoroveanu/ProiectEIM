package com.aj.proiecteim.tema6

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reply.ui.theme.MaterialThemeEIM

class SecondActivity : ComponentActivity() {
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
                    DisplayText(context = this)
                }
            }
        }
    }
}

@Composable
fun DisplayText(context: ComponentActivity) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Șir de caractere: ${context.intent.getStringExtra("stringInput")}",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            "Număr: ${context.intent.getStringExtra("numberInput")}",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
