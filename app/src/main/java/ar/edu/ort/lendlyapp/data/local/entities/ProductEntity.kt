package ar.edu.ort.lendlyapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val category: String?,
    val price: Double,
    val currency: String?,
    val image: String?,
    val monthlyInstallment: Double?,
    val installmentMonths: Int?,
    val interestRate: Double?,
    val isFeatured: Boolean?,
    val isAvailable: Boolean?,
    val rating: Double?,
    val reviewCount: Int?,
    val description: String?
)
