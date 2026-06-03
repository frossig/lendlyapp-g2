package ar.edu.ort.lendlyapp.data.repository

import ar.edu.ort.lendlyapp.data.remote.ApiService
import ar.edu.ort.lendlyapp.data.remote.dto.AppliedLoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.ApplyLoanRequest
import ar.edu.ort.lendlyapp.data.remote.dto.LoansResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getLoans(): LoansResponse = api.getLoans()

    suspend fun applyLoan(
        amount: Double,
        installments: Int,
        purpose: String?
    ): AppliedLoanDto =
        api.applyLoan(
            ApplyLoanRequest(
                amount = amount,
                installments = installments,
                purpose = purpose
            )
        ).loan
}