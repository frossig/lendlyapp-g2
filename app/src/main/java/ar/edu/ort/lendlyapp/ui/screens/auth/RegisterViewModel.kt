package ar.edu.ort.lendlyapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.remote.dto.CreateUserRequest
import ar.edu.ort.lendlyapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class RegisterStep {
    PHONE, SMS, FACE, ID, VERIFIED, PROFILE, SIGNATURE, PASSWORD, DONE
}

data class RegisterUiState(
    val step: RegisterStep = RegisterStep.PHONE,
    val countryCode: String = "+65",
    val phone: String = "",
    val smsCode: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val day: String = "",
    val month: String = "",
    val year: String = "",
    val address: String = "",
    val city: String = "",
    val postalCode: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
) {
    val fullPhone: String get() = "$countryCode$phone"
    val birthDate: String get() = "$year-${month.padStart(2, '0')}-${day.padStart(2, '0')}"

    val phoneValid: Boolean get() = phone.length >= 6 && countryCode.isNotBlank()
    val smsValid: Boolean get() = smsCode.length == 6
    val profileValid: Boolean
        get() = firstName.isNotBlank() && lastName.isNotBlank() &&
                day.isNotBlank() && month.isNotBlank() && year.length == 4 &&
                address.isNotBlank() && city.isNotBlank() && postalCode.isNotBlank()
    val passwordValid: Boolean
        get() = password.length >= 9 &&
                password.any(Char::isLetter) &&
                password.any(Char::isDigit)
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onCountryCodeChange(value: String) = _uiState.update { it.copy(countryCode = value) }
    fun onPhoneChange(value: String) = _uiState.update { it.copy(phone = value, error = null) }
    fun onSmsCodeChange(value: String) = _uiState.update { it.copy(smsCode = value, error = null) }
    fun onFirstNameChange(value: String) = _uiState.update { it.copy(firstName = value) }
    fun onLastNameChange(value: String) = _uiState.update { it.copy(lastName = value) }
    fun onDayChange(value: String) = _uiState.update { it.copy(day = value) }
    fun onMonthChange(value: String) = _uiState.update { it.copy(month = value) }
    fun onYearChange(value: String) = _uiState.update { it.copy(year = value) }
    fun onAddressChange(value: String) = _uiState.update { it.copy(address = value) }
    fun onCityChange(value: String) = _uiState.update { it.copy(city = value) }
    fun onPostalCodeChange(value: String) = _uiState.update { it.copy(postalCode = value) }
    fun onPasswordChange(value: String) = _uiState.update { it.copy(password = value) }
    fun togglePasswordVisibility() = _uiState.update { it.copy(showPassword = !it.showPassword) }

    fun nextStep() {
        val current = _uiState.value.step
        val next = RegisterStep.entries.getOrNull(current.ordinal + 1) ?: return
        _uiState.update { it.copy(step = next) }
    }

    fun previousStep(): Boolean {
        val current = _uiState.value.step
        val prev = RegisterStep.entries.getOrNull(current.ordinal - 1) ?: return false
        _uiState.update { it.copy(step = prev) }
        return true
    }

    fun submitRegistration() {
        val s = _uiState.value
        _uiState.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                authRepository.register(
                    CreateUserRequest(
                        firstName = s.firstName,
                        lastName = s.lastName,
                        phone = s.fullPhone,
                        password = s.password,
                        birthDate = s.birthDate,
                        address = s.address,
                        city = s.city,
                        postalCode = s.postalCode
                    )
                )
                _uiState.update { it.copy(loading = false, step = RegisterStep.DONE) }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = t.message ?: "No se pudo crear la cuenta"
                    )
                }
            }
        }
    }
}
