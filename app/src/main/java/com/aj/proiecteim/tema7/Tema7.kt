package com.aj.proiecteim.tema7

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aj.proiecteim.tema7.ui.AddAddressScreen
import com.aj.proiecteim.tema7.ui.AddUserScreen
import com.aj.proiecteim.tema7.viewmodels.AddressViewModel
import com.aj.proiecteim.tema7.viewmodels.UserViewModel
import com.example.reply.ui.theme.MaterialThemeEIM

class Tema7 : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val addressViewModel: AddressViewModel by viewModels()

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
                        Options()
                    }
                }
            }
        }
    }

    @Composable
    fun Options() {
        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmare ștergere") },
                text = { Text("Sigur doriți să ștergeți toate datele? Această acțiune nu poate fi înapoiată.") },
                confirmButton = {
                    Button(onClick = {
                        userViewModel.deleteAllUsers()
                        addressViewModel.deleteAllAddresses()
                        showDialog = false
                        Toast.makeText(this@Tema7, "Datele au fost șterse", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Ștergere")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Anulare")
                    }
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(this@Tema7, AddAddressScreen::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 20.sp)) {
                            append("Adăugați adresă\n")
                        }
                        append("Adresa este deținută de utilizator")
                    },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    val intent = Intent(this@Tema7, AddUserScreen::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 20.sp)) {
                            append("Adăugați utilizator\n")
                        }
                        append("Utilizatorul deține o adresă")
                    },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 20.sp)) {
                            append("Ștergeți datele\n")
                        }
                        append("din ambele tabele")
                    },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


