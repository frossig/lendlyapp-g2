package ar.edu.ort.lendlyapp.ui.screens.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.local.SessionManager
import ar.edu.ort.lendlyapp.data.remote.dto.UserDto
import ar.edu.ort.lendlyapp.data.repository.HomeRepository
import ar.edu.ort.lendlyapp.ui.components.PersonalDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditAccountUiState(
    val loading: Boolean = true,
    val error: String? = null,
    val form: PersonalDetailsState = PersonalDetailsState(),
    val saving: Boolean = false
)

@HiltViewModel
class EditAccountDetailsViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditAccountUiState())
    val uiState: StateFlow<EditAccountUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            val userId = sessionManager.userId.first()
            if (userId.isNullOrBlank()) {
                _uiState.update { it.copy(loading = false, error = "Sesión expirada") }
                return@launch
            }
            try {
                val user = homeRepository.getUser(userId)
                _uiState.update { it.copy(loading = false, form = userToForm(user)) }
            } catch (t: Throwable) {
                _uiState.update { it.copy(loading = false, error = t.message ?: "Error") }
            }
        }
    }

    fun onFormChange(form: PersonalDetailsState) {
        _uiState.update { it.copy(form = form) }
    }

    fun save(onSaved: () -> Unit) {
        // No hay endpoint PUT /users/{id} en el mock — el save es solo simulación.
        _uiState.update { it.copy(saving = true) }
        viewModelScope.launch {
            _uiState.update { it.copy(saving = false) }
            onSaved()
        }
    }
}

private fun userToForm(user: UserDto): PersonalDetailsState {
    val (firstName, lastName) = splitName(user.fullName)
    val (year, month, day) = splitDate(user.birthDate)
    val (countryCode, phone) = splitPhone(user.phone)
    return PersonalDetailsState(
        firstName = firstName,
        lastName = lastName,
        day = day, month = month, year = year,
        address = user.address.orEmpty(),
        city = "",
        postalCode = "",
        countryCode = countryCode,
        phone = phone
    )
}

private fun splitName(fullName: String): Pair<String, String> {
    val parts = fullName.trim().split(" ", limit = 2)
    return parts.getOrNull(0).orEmpty() to parts.getOrNull(1).orEmpty()
}

private fun splitDate(iso: String?): Triple<String, String, String> {
    if (iso.isNullOrBlank()) return Triple("", "", "")
    val parts = iso.split("-")
    return Triple(
        parts.getOrNull(0).orEmpty(),
        parts.getOrNull(1).orEmpty().trimStart('0').padStart(1, '0'),
        parts.getOrNull(2).orEmpty().trimStart('0').padStart(1, '0')
    )
}

private fun splitPhone(phone: String): Pair<String, String> {
    val match = Regex("""^(\+\d+)[-]?(.*)$""").matchEntire(phone)
    return if (match != null) match.groupValues[1] to match.groupValues[2] else "+65" to phone
}
