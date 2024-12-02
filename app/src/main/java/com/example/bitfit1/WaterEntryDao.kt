package com.example.bitfit1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterEntryDao {
    @Query("SELECT * FROM water_entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<WaterEntryEntity>>

    @Insert
    fun insertEntry(entry: WaterEntryEntity)

    @Query("DELETE FROM water_entries")
    fun deleteAllEntries()
}
