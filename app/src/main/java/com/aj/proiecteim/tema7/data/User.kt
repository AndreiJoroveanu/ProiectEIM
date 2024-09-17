package com.aj.proiecteim.tema7.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    foreignKeys = [ForeignKey(
        entity = Address::class,
        parentColumns = ["addressId"],
        childColumns = ["addressId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val addressId: Int,
    val name: String,
    val age: Int,
    val email: String
)
