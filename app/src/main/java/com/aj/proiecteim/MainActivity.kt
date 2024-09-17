package com.aj.proiecteim

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aj.proiecteim.tema2.Tema2
import com.aj.proiecteim.tema3.Tema3
import com.aj.proiecteim.tema4.Tema4
import com.aj.proiecteim.tema5.Tema5
import com.aj.proiecteim.tema6.Tema6
import com.aj.proiecteim.tema7.Tema7
import com.aj.proiecteim.tema8.Tema8
import com.example.reply.ui.theme.MaterialThemeEIM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        this.actionBar?.hide()

        super.onCreate(savedInstanceState)

        setContent {
            Box(Modifier.safeDrawingPadding()) {
                MaterialThemeEIM {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Proiect EIM\nJoroveanu Andrei",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(Cyan, Color(0xFF0066FF), Color(0xFF800080))
                    ),
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 4f
                    )
                )
            )

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, Tema2::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("Tema 1 + 2\n")
                    }
                    append("Obiecte + filtrare")
                },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, Tema3::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("Tema 3\n")
                    }
                    append("RSS Reader")
                },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, Tema4::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("Tema 4\n")
                    }
                    append("Internaționalizare")
                },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, Tema5::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("Tema 5\n")
                    }
                    append("Scriere/citire din fișier")
                },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, Tema6::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("Tema 6\n")
                    }
                    append("Transmitere date")
                },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, Tema7::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("Tema 7\n")
                    }
                    append("Operații CRUD")
                },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, Tema8::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append("Tema 8\n")
                    }
                    append("Integrare hartă")
                },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
