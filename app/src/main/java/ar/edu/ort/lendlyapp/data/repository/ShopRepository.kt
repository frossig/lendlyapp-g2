package ar.edu.ort.lendlyapp.data.repository

import ar.edu.ort.lendlyapp.data.remote.ApiService
import ar.edu.ort.lendlyapp.data.remote.dto.BrandDto
import ar.edu.ort.lendlyapp.data.remote.dto.CategoryDto
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import javax.inject.Inject
import javax.inject.Singleton

data class ShopData(
    val featured: List<ProductDto>,
    val categories: List<CategoryDto>,
    val brands: List<BrandDto>,
    val products: List<ProductDto>,
    val bestSellers: List<ProductDto>
)

@Singleton
class ShopRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getShopData(): ShopData {
        val res = api.getProducts()
        val products = res.products
        return ShopData(
            featured = res.featured ?: emptyList(),
            categories = res.categories ?: emptyList(),
            brands = res.brands ?: emptyList(),
            products = products,
            bestSellers = products.sortedByDescending { it.reviewCount ?: 0 }.take(8)
        )
    }
}
