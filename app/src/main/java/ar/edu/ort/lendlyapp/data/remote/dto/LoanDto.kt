package ar.edu.ort.lendlyapp.data.remote.dto

data class LoanDto(
    val id: String,
    val brand: String,
    val brandLogoUrl: String?,
    val amount: Double,
    val dueDate: String,
    val status: String,
    val installments: Int?
)

data class ApplyLoanRequest(
    val amount: Double,
    val installments: Int,
    val purpose: String?
)

data class ApplyLoanResponse(
    val loanId: String,
    val status: String
)
