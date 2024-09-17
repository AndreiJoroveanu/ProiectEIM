package com.aj.proiecteim.tema7.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.proiecteim.tema7.data.Address
import com.aj.proiecteim.tema7.data.AppDatabase
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val database: AppDatabase) : ViewModel() {
    private val userDao = database.userDao()

    private val _addresses = MutableLiveData<List<Address>>()
    val addresses: LiveData<List<Address>> get() = _addresses

    fun loadAddresses(userId: Int) {
        viewModelScope.launch {
            userDao.getAddressesForUser(userId).collect { addressList ->
                _addresses.value = addressList
            }
        }
    }
}
