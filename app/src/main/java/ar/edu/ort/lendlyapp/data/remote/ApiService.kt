package ar.edu.ort.lendlyapp.data.remote

import ar.edu.ort.lendlyapp.data.remote.dto.ApplyLoanRequest
import ar.edu.ort.lendlyapp.data.remote.dto.ApplyLoanResponse
import ar.edu.ort.lendlyapp.data.remote.dto.CreateUserRequest
import ar.edu.ort.lendlyapp.data.remote.dto.CreateUserResponse
import ar.edu.ort.lendlyapp.data.remote.dto.LoansResponse
import ar.edu.ort.lendlyapp.data.remote.dto.LoginRequest
import ar.edu.ort.lendlyapp.data.remote.dto.LoginResponse
import ar.edu.ort.lendlyapp.data.remote.dto.ProductsResponse
import ar.edu.ort.lendlyapp.data.remote.dto.TransactionsResponse
import ar.edu.ort.lendlyapp.data.remote.dto.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("auth/create")
    suspend fun register(@Body body: CreateUserRequest): CreateUserResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): UserResponse

    @GET("loans")
    suspend fun getLoans(): LoansResponse

    @POST("loans/apply")
    suspend fun applyLoan(@Body body: ApplyLoanRequest): ApplyLoanResponse

    @GET("transactions")
    suspend fun getTransactions(): TransactionsResponse

    @GET("products")
    suspend fun getProducts(): ProductsResponse
}
