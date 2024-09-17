package com.aj.proiecteim.tema7.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aj.proiecteim.tema7.data.AppDatabase

class UserDetailsViewModelFactory(
    private val database: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailsViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
