package com.aj.proiecteim.tema7.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.aj.proiecteim.tema7.data.User
import com.aj.proiecteim.tema7.viewmodels.UserViewModel
import com.example.reply.ui.theme.MaterialThemeEIM
import kotlinx.coroutines.launch

class AddUserScreen : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

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
                    UserTextFields(userViewModel, this)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTextFields(userViewModel: UserViewModel = viewModel(), context: ComponentActivity) {
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var addresses by remember { mutableStateOf<List<Address>>(emptyList()) }
    var selectedAddress by remember { mutableStateOf<Address?>(null) }

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userViewModel.getAllAddresses().collect { addressList ->
            addresses = addressList
        }
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nume") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Vârstă") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedAddress?.street ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Adresă") },
                shape = RoundedCornerShape(100.dp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor().clickable { expanded = true }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                addresses.forEach { address ->
                    DropdownMenuItem(
                        text = { Text("${address.street}, ${address.city}", style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            selectedAddress = address
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val ageInt = age.toIntOrNull()
                if (selectedAddress == null || name.isBlank() || ageInt == null || email.isBlank())
                    Toast.makeText(context, "Introduceți valori valide", Toast.LENGTH_SHORT).show()
                else scope.launch {
                    selectedAddress?.let { address ->
                        userViewModel.insertUser(User(addressId = address.addressId, name = name, age = ageInt, email = email))
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Adăugare utilizator",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val navController = rememberNavController()
        NavHost(navController, startDestination = "userList") {
            composable("userList") { UserListScreen(navController) }
            composable("userDetails/{userId}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: return@composable
                UserDetailsScreen(userId = userId, navController = navController, userViewModel = userViewModel)
            }
        }
    }
}