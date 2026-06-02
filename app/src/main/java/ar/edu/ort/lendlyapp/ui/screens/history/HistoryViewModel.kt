package ar.edu.ort.lendlyapp.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.TransactionDto
import ar.edu.ort.lendlyapp.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class HistoryFilter(val label: String) {
    ALL("All"),
    TYPE("Type"),
    BALANCE("Balance"),
    PAID_BILLS("Paid Bills"),
    ADDED("Added")
}

data class HistoryUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val transactions: List<TransactionDto> = emptyList(),
    val paidLoans: List<LoanDto> = emptyList(),
    val query: String = "",
    val filter: HistoryFilter = HistoryFilter.ALL
) {
    val filteredTransactions: List<TransactionDto>
        get() {
            val byFilter = when (filter) {
                HistoryFilter.ALL -> transactions
                HistoryFilter.PAID_BILLS -> transactions.filter { it.type == "LOAN_PAYMENT" }
                HistoryFilter.ADDED -> transactions.filter {
                    it.type == "CASH_IN" || it.type == "LOAN_DISBURSEMENT"
                }
                HistoryFilter.BALANCE -> transactions.filter { it.amount > 0 }
                HistoryFilter.TYPE -> transactions
            }
            val q = query.trim().lowercase()
            return if (q.isBlank()) byFilter
            else byFilter.filter {
                it.title.lowercase().contains(q) ||
                    (it.description?.lowercase()?.contains(q) ?: false)
            }
        }
}

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        _uiState.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                val txDef = async { repository.getTransactions() }
                val loansDef = async { repository.getLoans() }
                awaitAll(txDef, loansDef)
                _uiState.update {
                    it.copy(
                        loading = false,
                        transactions = txDef.getCompleted(),
                        paidLoans = loansDef.getCompleted().filter { l -> l.status == "PAID" }
                    )
                }
            } catch (t: Throwable) {
                _uiState.update { it.copy(loading = false, error = t.message ?: "Error") }
            }
        }
    }

    fun onQueryChange(value: String) {
        _uiState.update { it.copy(query = value) }
    }

    fun onFilterChange(value: HistoryFilter) {
        _uiState.update { it.copy(filter = value) }
    }
}
