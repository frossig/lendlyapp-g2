package com.example.simulacro.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simulacro.data.remote.ItemDto
import com.example.simulacro.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Patrón MVVM:
 * - El ViewModel mantiene el estado de la pantalla y sobrevive a rotaciones.
 * - La UI observa `uiState` y se redibuja cuando cambia.
 * - El ViewModel NUNCA conoce a Compose ni a la Activity (es testeable solo).
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                HomeUiState.Success(repository.getItems())
            } catch (t: Throwable) {
                HomeUiState.Error(t.message ?: "Error desconocido")
            }
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val items: List<ItemDto>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
