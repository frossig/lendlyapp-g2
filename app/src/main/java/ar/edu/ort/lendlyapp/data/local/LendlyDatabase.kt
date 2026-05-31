package ar.edu.ort.lendlyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class LendlyDatabase : RoomDatabase()
