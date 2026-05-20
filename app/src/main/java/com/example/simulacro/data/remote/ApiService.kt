package com.example.simulacro.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Definición de los endpoints de la API REST externa.
 *
 * Cada función `suspend` corresponde a un endpoint. Retrofit genera la
 * implementación automáticamente; vos solo declarás el contrato.
 *
 * Reemplazar estos endpoints de ejemplo cuando se sepa la API del parcial.
 */
interface ApiService {

    @GET("items")
    suspend fun getItems(): List<ItemDto>

    @GET("items/{id}")
    suspend fun getItem(@Path("id") id: String): ItemDto
}

/**
 * DTO = Data Transfer Object. Es la forma EXACTA en la que llega el JSON
 * desde el servidor. Después se mapea a un modelo de dominio si hace falta.
 */
data class ItemDto(
    val id: String,
    val name: String,
    val imageUrl: String?
)
