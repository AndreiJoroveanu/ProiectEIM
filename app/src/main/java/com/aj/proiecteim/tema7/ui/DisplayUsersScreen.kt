package com.aj.proiecteim.tema7.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.aj.proiecteim.tema7.data.AppDatabase
import com.aj.proiecteim.tema7.viewmodels.UserDetailsViewModel
import com.aj.proiecteim.tema7.viewmodels.UserDetailsViewModelFactory
import com.aj.proiecteim.tema7.viewmodels.UserViewModel

@Composable
fun UserListScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    val users by userViewModel.users.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        users.forEach { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("userDetails/${user.userId}") }
                    .padding(16.dp)
            ) {
                Text(text = "${user.name}, ${user.age}", modifier = Modifier.weight(1f))
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun UserDetailsScreen(userId: Int, navController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    val user = userViewModel.users.collectAsState().value.find { it.userId == userId }

    val userDetailsViewModel: UserDetailsViewModel = viewModel(
        factory = UserDetailsViewModelFactory(AppDatabase.getDatabase(LocalContext.current))
    )

    LaunchedEffect(userId) {
        userDetailsViewModel.loadAddresses(userId)
    }

    val addresses by userDetailsViewModel.addresses.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            user?.let {
                Text(text = "Nume: ${it.name}")
                Text(text = "Vârstă: ${it.age}")
                Text(text = "Email: ${it.email}")

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Adrese:")
                addresses.forEach { address ->
                    Text(text = "${address.street}, ${address.city}, ${address.postalCode}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Button(
                onClick = {
                    if (user != null) {
                        userViewModel.deleteUser(user)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    "Ștergere utilizator",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
