package ar.edu.ort.lendlyapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.local.SessionManager
import ar.edu.ort.lendlyapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val savedFullName: String? = null,
    val savedPhone: String? = null,
    val phoneInput: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isEditing: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {
    val hasSavedUser: Boolean
        get() = !savedFullName.isNullOrBlank() && !savedPhone.isNullOrBlank()

    val showCard: Boolean
        get() = hasSavedUser && !isEditing

    val effectivePhone: String
        get() = if (showCard) savedPhone.orEmpty() else phoneInput

    val canSubmit: Boolean
        get() = effectivePhone.isNotBlank() && password.length >= 4 && !loading
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val name = sessionManager.savedFullName.first()
            val phone = sessionManager.savedPhone.first()
            _uiState.update { it.copy(savedFullName = name, savedPhone = phone) }
        }
    }

    fun onPhoneChange(value: String) {
        _uiState.update { it.copy(phoneInput = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun toggleEditing() {
        _uiState.update { it.copy(isEditing = !it.isEditing) }
    }

    fun submit() {
        val current = _uiState.value
        if (!current.canSubmit) return
        _uiState.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                authRepository.login(current.effectivePhone, current.password)
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
