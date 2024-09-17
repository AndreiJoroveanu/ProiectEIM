package com.aj.proiecteim.tema7.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey(autoGenerate = true) val addressId: Int = 0,
    val street: String,
    val city: String,
    val postalCode: Int
)
