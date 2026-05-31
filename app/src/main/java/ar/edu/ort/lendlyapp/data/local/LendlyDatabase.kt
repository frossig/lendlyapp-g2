package ar.edu.ort.lendlyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Base Room. Por ahora sin entidades — está lista para cuando alguien quiera
 * cachear productos, préstamos o transacciones localmente.
 *
 * Cómo agregar una entidad:
 *   1) crear `entities/MyEntity.kt` con @Entity
 *   2) crear `daos/MyDao.kt` con @Dao y queries
 *   3) sumar a `entities = [...]` acá abajo y subir `version` en 1
 *   4) agregar `abstract fun myDao(): MyDao`
 *   5) exponer el DAO en DatabaseModule
 */
@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class LendlyDatabase : RoomDatabase()
