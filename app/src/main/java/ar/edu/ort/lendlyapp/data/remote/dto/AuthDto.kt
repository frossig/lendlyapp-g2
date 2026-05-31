package ar.edu.ort.lendlyapp.data.remote.dto


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
