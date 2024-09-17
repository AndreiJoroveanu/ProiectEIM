package com.aj.proiecteim.tema7.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAddress(address: Address): Long

    @Update
    suspend fun updateAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("DELETE FROM addresses")
    suspend fun deleteAllAddresses()

    @Query("SELECT * FROM addresses")
    fun getAllAddresses(): Flow<List<Address>>
}
