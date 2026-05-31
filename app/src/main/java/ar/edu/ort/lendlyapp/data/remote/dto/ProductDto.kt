package ar.edu.ort.lendlyapp.data.remote.dto

data class ProductDto(
    val id: String,
    val name: String,
    val brand: String,
    val price: Double,
    val installmentAmount: Double?,
    val installmentCount: Int?,
    val imageUrl: String?,
    val category: String?
)
