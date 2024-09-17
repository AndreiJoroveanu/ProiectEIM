package com.aj.proiecteim.tema7.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aj.proiecteim.tema7.data.Address
import com.aj.proiecteim.tema7.viewmodels.AddressViewModel
import com.example.reply.ui.theme.MaterialThemeEIM
import kotlinx.coroutines.launch

class AddAddressScreen : ComponentActivity() {
    private val addressViewModel: AddressViewModel by viewModels()

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
                    AddressTextFields(addressViewModel, this)
                }
            }
        }
    }
}

@Composable
fun AddressTextFields(addressViewModel: AddressViewModel = viewModel(), context: ComponentActivity) {
    val scope = rememberCoroutineScope()
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = street,
            onValueChange = { street = it },
            label = { Text("Stradă") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Oraș") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        OutlinedTextField(
            value = postalCode,
            onValueChange = { postalCode = it },
            label = { Text("Cod poștal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val postalCodeInt = postalCode.toIntOrNull()
                if (street.isBlank() || city.isBlank() || postalCodeInt == null)
                    Toast.makeText(context, "Introduceți valori valide", Toast.LENGTH_SHORT).show()
                else scope.launch {
                    addressViewModel.insertAddress(Address(street = street, city = city, postalCode = postalCodeInt))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Adăugare adresă",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val navController = rememberNavController()
        NavHost(navController, startDestination = "addressList") {
            composable("addressList") { AddressListScreen(navController) }
            composable("addressDetails/{addressId}") { backStackEntry ->
                val addressId = backStackEntry.arguments?.getString("addressId")?.toInt() ?: return@composable
                AddressDetailsScreen(addressId = addressId, navController = navController, addressViewModel = addressViewModel)
            }
        }
    }
}
