package ar.edu.ort.lendlyapp.data.remote.dto

data class ProductsResponse(
    val success: Boolean,
    val pagination: PaginationDto?,
    val featured: List<ProductDto>?,
    val categories: List<CategoryDto>?,
    val brands: List<BrandDto>?,
    val products: List<ProductDto>
)

data class ProductDto(
    val id: String,
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

data class CategoryDto(
    val id: String,
    val name: String,
    val icon: String?,
    val productCount: Int?
)

data class BrandDto(
    val id: String,
    val name: String,
    val logo: String?
)
