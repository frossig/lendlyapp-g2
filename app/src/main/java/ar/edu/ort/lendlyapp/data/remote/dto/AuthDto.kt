package ar.edu.ort.lendlyapp.data.remote.dto

data class LoginRequest(
    val phone: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val user: AuthUserDto
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
    val success: Boolean,
    val message: String?,
    val token: String,
    val user: AuthUserDto
)

data class AuthUserDto(
    val id: Int,
    val fullName: String,
    val phone: String,
    val email: String?,
    val avatar: String?,
    val creditScore: Int?,
    val availableBalance: Double?,
    val memberSince: String?
)
