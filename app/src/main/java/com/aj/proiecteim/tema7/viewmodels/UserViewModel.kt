package com.aj.proiecteim.tema7.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aj.proiecteim.tema7.data.Address
import com.aj.proiecteim.tema7.data.AppDatabase
import com.aj.proiecteim.tema7.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val addressDao = AppDatabase.getDatabase(application).addressDao()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _addresses = MutableStateFlow<List<Address>>(emptyList())
    val addresses: StateFlow<List<Address>> get() = _addresses

    init {
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        viewModelScope.launch {
            userDao.getAllUsers()
                .collect { userList ->
                    _users.value = userList
                }
        }
    }

    fun insertUser(user: User) = viewModelScope.launch {
        userDao.insertUser(user)
        fetchAllUsers()
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        userDao.deleteUser(user)
        fetchAllUsers()
    }

    fun deleteAllUsers() = viewModelScope.launch {
        userDao.deleteAllUsers()
        fetchAllUsers()
    }

    fun getAllAddresses() = addressDao.getAllAddresses()
}
