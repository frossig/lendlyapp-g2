package ar.edu.ort.lendlyapp.ui.screens.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import ar.edu.ort.lendlyapp.data.repository.FavoritesRepository
import ar.edu.ort.lendlyapp.data.repository.ShopData
import ar.edu.ort.lendlyapp.data.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 2

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
    val filter: ShopFilter = ShopFilter(),
    val visibleProductsCount: Int = PAGE_SIZE,
    val visibleBestSellersCount: Int = PAGE_SIZE,
    val favoriteIds: Set<String> = emptySet()
)

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState())
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        _uiState.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                val data = shopRepository.getShopData()
                val favoriteIds = runCatching { favoritesRepository.getFavoriteIds() }
                    .getOrDefault(emptySet())
                _uiState.update {
                    it.copy(
                        loading = false,
                        data = data,
                        visibleProductsCount = PAGE_SIZE,
                        visibleBestSellersCount = PAGE_SIZE,
                        favoriteIds = favoriteIds
                    )
                }
            } catch (t: Throwable) {
                _uiState.update { it.copy(loading = false, error = t.message ?: "Error") }
            }
        }
    }

    fun loadMoreProducts() {
        _uiState.update { it.copy(visibleProductsCount = it.visibleProductsCount + PAGE_SIZE) }
    }

    fun loadMoreBestSellers() {
        _uiState.update { it.copy(visibleBestSellersCount = it.visibleBestSellersCount + PAGE_SIZE) }
    }

    fun toggleFavorite(product: ProductDto) {
        val current = _uiState.value.favoriteIds
        val isFavorite = product.id in current
        _uiState.update {
            it.copy(
                favoriteIds = if (isFavorite) current - product.id else current + product.id
            )
        }
        viewModelScope.launch {
            runCatching {
                if (isFavorite) favoritesRepository.removeFavorite(product.id)
                else favoritesRepository.addFavorite(product)
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
