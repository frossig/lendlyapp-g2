package ar.edu.ort.lendlyapp.data.remote.dto

data class UserResponse(
    val success: Boolean,
    val user: UserDto
)

data class UserDto(
    val id: Int,
    val fullName: String,
    val phone: String,
    val email: String?,
    val avatar: String?,
    val birthDate: String?,
    val address: String?,
    val creditScore: Int?,
    val creditLevel: String?,
    val availableBalance: Double?,
    val totalLoanLimit: Double?,
    val memberSince: String?,
    val isVerified: Boolean?,
    val notifications: NotificationPrefsDto?
)

data class NotificationPrefsDto(
    val push: Boolean,
    val email: Boolean,
    val sms: Boolean
)
