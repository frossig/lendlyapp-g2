package ar.edu.ort.lendlyapp.ui.screens.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.repository.ShopData
import ar.edu.ort.lendlyapp.data.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShopFilter(
    val brand: String = "All",
    val gender: String = "All",
    val sortBy: String = "Most Recent",
    val priceRange: String = "All"
)

data class ShopUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: ShopData? = null,
    val query: String = "",
    val filter: ShopFilter = ShopFilter()
)

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState())
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        _uiState.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                val data = shopRepository.getShopData()
                _uiState.update { it.copy(loading = false, data = data) }
            } catch (t: Throwable) {
                _uiState.update { it.copy(loading = false, error = t.message ?: "Error") }
            }
        }
    }

    fun onQueryChange(value: String) {
        _uiState.update { it.copy(query = value) }
    }

    fun setFilter(filter: ShopFilter) {
        _uiState.update { it.copy(filter = filter) }
    }
}
