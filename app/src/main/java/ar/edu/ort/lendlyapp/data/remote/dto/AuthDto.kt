package ar.edu.ort.lendlyapp.data.remote.dto

/**
 * DTOs para los endpoints de /auth.
 *
 * Estos campos son estimados desde el spec; cuando peguen al mock real
 * conviene revisarlos contra la respuesta que devuelve Postman y ajustarlos.
 */

data class LoginRequest(
    val phone: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val userId: String
)

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
    val birthDate: String,
    val address: String,
    val city: String,
    val postalCode: String
)

data class CreateUserResponse(
    val token: String,
    val userId: String
)
