package ar.edu.ort.lendlyapp.data.repository

import ar.edu.ort.lendlyapp.data.remote.ApiService
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.TransactionDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getTransactions(): List<TransactionDto> = api.getTransactions().transactions

    suspend fun getLoans(): List<LoanDto> = api.getLoans().loans
}
