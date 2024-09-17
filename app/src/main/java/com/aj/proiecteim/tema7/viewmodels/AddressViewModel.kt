package com.aj.proiecteim.tema7.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aj.proiecteim.tema7.data.Address
import com.aj.proiecteim.tema7.data.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddressViewModel(application: Application) : AndroidViewModel(application) {
    private val addressDao = AppDatabase.getDatabase(application).addressDao()

    private val _addresses = MutableStateFlow<List<Address>>(emptyList())
    val addresses: StateFlow<List<Address>> get() = _addresses

    init {
        fetchAllAddresses()
    }

    private fun fetchAllAddresses() {
        viewModelScope.launch {
            addressDao.getAllAddresses()
                .collect { addressList ->
                    _addresses.value = addressList
                }
        }
    }

    fun insertAddress(address: Address) = viewModelScope.launch {
        addressDao.insertAddress(address)
        fetchAllAddresses()
    }

    fun deleteAddress(address: Address) = viewModelScope.launch {
        addressDao.deleteAddress(address)
        fetchAllAddresses()
    }

    fun deleteAllAddresses() = viewModelScope.launch {
        addressDao.deleteAllAddresses()
        fetchAllAddresses()
    }
}
