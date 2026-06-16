package ar.edu.ort.lendlyapp.data.repository

import ar.edu.ort.lendlyapp.data.local.dao.ProductDao
import ar.edu.ort.lendlyapp.data.local.mappers.toDto
import ar.edu.ort.lendlyapp.data.local.mappers.toEntity
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
    val bestSellers: List<ProductDto>,
    val fromCache: Boolean = false
)

@Singleton
class ShopRepository @Inject constructor(
    private val api: ApiService,
    private val productDao: ProductDao
) {
    suspend fun getShopData(): ShopData {
        return try {
            val res = api.getProducts()
            productDao.upsertAll(res.products.map { it.toEntity() })
            val products = res.products
            ShopData(
                featured = res.featured ?: emptyList(),
                categories = res.categories ?: emptyList(),
                brands = res.brands ?: emptyList(),
                products = products,
                bestSellers = products.sortedByDescending { it.reviewCount ?: 0 }.take(8)
            )
        } catch (t: Throwable) {
            val cached = productDao.getAll().map { it.toDto() }
            if (cached.isEmpty()) throw t
            ShopData(
                featured = cached.filter { it.isFeatured == true },
                categories = emptyList(),
                brands = emptyList(),
                products = cached,
                bestSellers = cached.sortedByDescending { it.reviewCount ?: 0 }.take(8),
                fromCache = true
            )
        }
    }
}
