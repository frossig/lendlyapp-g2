package ar.edu.ort.lendlyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.ort.lendlyapp.data.local.dao.ProductDao
import ar.edu.ort.lendlyapp.data.local.entities.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LendlyDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
