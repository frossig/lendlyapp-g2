package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.remote.dto.AppliedLoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.LoanSummaryDto
import ar.edu.ort.lendlyapp.data.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoansUiState {
    data object Loading : LoansUiState
    data class Success(
        val present: List<LoanDto>,
        val recent: List<LoanDto>,
        val summary: LoanSummaryDto?
    ) : LoansUiState
    data class Error(val message: String) : LoansUiState
}

sealed interface ApplyLoanUiState {
    data object Idle : ApplyLoanUiState
    data object Submitting : ApplyLoanUiState
    data class Success(val loan: AppliedLoanDto) : ApplyLoanUiState
    data class Error(val message: String) : ApplyLoanUiState
}

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val loanRepository: LoanRepository
) : ViewModel() {

    private val _loansState = MutableStateFlow<LoansUiState>(LoansUiState.Loading)
    val loansState: StateFlow<LoansUiState> = _loansState.asStateFlow()

    private val _applyState = MutableStateFlow<ApplyLoanUiState>(ApplyLoanUiState.Idle)
    val applyState: StateFlow<ApplyLoanUiState> = _applyState.asStateFlow()

    fun loadLoans() {
        _loansState.value = LoansUiState.Loading
        viewModelScope.launch {
            try {
                val res = loanRepository.getLoans()
                val paid = res.loans.filter { it.status.equals("PAID", ignoreCase = true) }
                val active = res.loans.filter { !it.status.equals("PAID", ignoreCase = true) }
                _loansState.value = LoansUiState.Success(
                    present = active,
                    recent = paid,
                    summary = res.summary
                )
            } catch (t: Throwable) {
                _loansState.value = LoansUiState.Error(
                    t.message ?: "No se pudieron cargar los préstamos"
                )
            }
        }
    }

    fun applyLoan(amount: Double, installments: Int, purpose: String?) {
        _applyState.value = ApplyLoanUiState.Submitting
        viewModelScope.launch {
            try {
                val loan = loanRepository.applyLoan(amount, installments, purpose)
                _applyState.value = ApplyLoanUiState.Success(loan)
            } catch (t: Throwable) {
                _applyState.value = ApplyLoanUiState.Error(
                    t.message ?: "No se pudo solicitar el préstamo"
                )
            }
        }
    }

    fun resetApply() {
        _applyState.value = ApplyLoanUiState.Idle
    }
}