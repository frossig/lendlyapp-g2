package ar.edu.ort.lendlyapp.data.remote

import ar.edu.ort.lendlyapp.data.remote.dto.ApplyLoanRequest
import ar.edu.ort.lendlyapp.data.remote.dto.ApplyLoanResponse
import ar.edu.ort.lendlyapp.data.remote.dto.CreateUserRequest
import ar.edu.ort.lendlyapp.data.remote.dto.CreateUserResponse
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.LoginRequest
import ar.edu.ort.lendlyapp.data.remote.dto.LoginResponse
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import ar.edu.ort.lendlyapp.data.remote.dto.TransactionDto
import ar.edu.ort.lendlyapp.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Endpoints definidos en el spec del parcial.
 * Base URL y header `x-api-key` se configuran en NetworkModule + AuthInterceptor.
 */
interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("auth/create")
    suspend fun register(@Body body: CreateUserRequest): CreateUserResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): UserDto

    @GET("loans")
    suspend fun getLoans(): List<LoanDto>

    @POST("loans/apply")
    suspend fun applyLoan(@Body body: ApplyLoanRequest): ApplyLoanResponse

    @GET("transactions")
    suspend fun getTransactions(): List<TransactionDto>

    @GET("products")
    suspend fun getProducts(): List<ProductDto>
}
