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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.aj.proiecteim.tema7.viewmodels.AddressViewModel

@Composable
fun AddressListScreen(navController: NavHostController, addressViewModel: AddressViewModel = viewModel()) {
    val addresses by addressViewModel.addresses.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        addresses.forEach { address ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("addressDetails/${address.addressId}") }
                    .padding(16.dp)
            ) {
                Text(text = "${address.street}, ${address.city}, ${address.postalCode}", modifier = Modifier.weight(1f))
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun AddressDetailsScreen(addressId: Int, navController: NavHostController, addressViewModel: AddressViewModel = viewModel()) {
    val address = addressViewModel.addresses.collectAsState().value.find { it.addressId == addressId }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            address?.let {
                Text(text = "Stradă: ${it.street}")
                Text(text = "Oraș: ${it.city}")
                Text(text = "Cod poștal: ${it.postalCode}")
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Button(
                onClick = {
                    if (address != null) {
                        addressViewModel.deleteAddress(address)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    "Ștergere adresă",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
