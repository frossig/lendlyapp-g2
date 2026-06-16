package ar.edu.ort.lendlyapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import ar.edu.ort.lendlyapp.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoritesUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val favorites: List<ProductDto> = emptyList()
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        _uiState.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                val favorites = favoritesRepository.getFavorites()
                _uiState.update { it.copy(loading = false, favorites = favorites) }
            } catch (t: Throwable) {
                _uiState.update { it.copy(loading = false, error = t.message ?: "Error") }
            }
        }
    }
}
