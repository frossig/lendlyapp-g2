package ar.edu.ort.lendlyapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val phone: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {
    val canSubmit: Boolean
        get() = phone.isNotBlank() && password.length >= 6 && !loading
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onPhoneChange(value: String) {
        _uiState.update { it.copy(phone = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun submit() {
        val current = _uiState.value
        if (!current.canSubmit) return

        _uiState.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                authRepository.login(current.phone, current.password)
                _uiState.update { it.copy(loading = false, success = true) }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = t.message ?: "No se pudo iniciar sesión"
                    )
                }
            }
        }
    }
}
