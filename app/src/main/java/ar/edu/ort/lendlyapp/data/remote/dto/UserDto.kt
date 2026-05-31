package ar.edu.ort.lendlyapp.data.remote.dto

data class UserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String?,
    val avatarUrl: String?,
    val creditScore: Int?,
    val availableBalance: Double?
)
