package ar.edu.ort.lendlyapp.ui.screens.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.remote.dto.TransactionDto
import ar.edu.ort.lendlyapp.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TransactionDetailsUiState {
    data object Loading : TransactionDetailsUiState
    data class Success(val transaction: TransactionDto) : TransactionDetailsUiState
    data class Error(val message: String) : TransactionDetailsUiState
}

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val txId: String = checkNotNull(savedStateHandle["txId"])

    private val _uiState = MutableStateFlow<TransactionDetailsUiState>(TransactionDetailsUiState.Loading)
    val uiState: StateFlow<TransactionDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = try {
                val tx = historyRepository.getTransactions().firstOrNull { it.id == txId }
                if (tx == null) TransactionDetailsUiState.Error("Transacción no encontrada")
                else TransactionDetailsUiState.Success(tx)
            } catch (t: Throwable) {
                TransactionDetailsUiState.Error(t.message ?: "Error")
            }
        }
    }
}
