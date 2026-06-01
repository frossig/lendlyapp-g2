package ar.edu.ort.lendlyapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.local.SessionManager
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import ar.edu.ort.lendlyapp.data.remote.dto.UserDto
import ar.edu.ort.lendlyapp.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val user: UserDto,
        val unpaidLoans: List<LoanDto>,
        val recommendedProducts: List<ProductDto>
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val userId = sessionManager.userId.first()
                    ?: throw IllegalStateException("No user id in session")
                val userDef = async { homeRepository.getUser(userId) }
                val loansDef = async { homeRepository.getLoans() }
                val productsDef = async { homeRepository.getRecommendedProducts() }
                awaitAll(userDef, loansDef, productsDef)

                _uiState.value = HomeUiState.Success(
                    user = userDef.getCompleted(),
                    unpaidLoans = loansDef.getCompleted().filter { it.status != "PAID" },
                    recommendedProducts = productsDef.getCompleted()
                )
            } catch (t: Throwable) {
                _uiState.value = HomeUiState.Error(t.message ?: "No se pudo cargar el Home")
            }
        }
    }
}
