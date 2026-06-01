package ar.edu.ort.lendlyapp.data.repository

import ar.edu.ort.lendlyapp.data.remote.ApiService
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import ar.edu.ort.lendlyapp.data.remote.dto.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getUser(id: String): UserDto = api.getUser(id).user

    suspend fun getLoans(): List<LoanDto> = api.getLoans().loans

    suspend fun getRecommendedProducts(): List<ProductDto> {
        val res = api.getProducts()
        return res.featured ?: res.products
    }
}
