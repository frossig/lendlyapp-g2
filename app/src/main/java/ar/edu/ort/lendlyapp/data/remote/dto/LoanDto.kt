package ar.edu.ort.lendlyapp.data.remote.dto

data class LoansResponse(
    val success: Boolean,
    val loans: List<LoanDto>,
    val summary: LoanSummaryDto?
)

data class LoanDto(
    val id: String,
    val lender: String,
    val lenderLogo: String?,
    val amount: Double,
    val amountDue: Double?,
    val installmentAmount: Double?,
    val installmentPlan: String?,
    val interestRate: Double?,
    val purpose: String?,
    val status: String,
    val nextPaymentDate: String?,
    val nextPaymentLabel: String?,
    val startDate: String?,
    val endDate: String?,
    val paidInstallments: Int?,
    val totalInstallments: Int?
)

data class LoanSummaryDto(
    val totalActive: Int,
    val totalPaid: Int,
    val totalAmountDue: Double
)

data class ApplyLoanRequest(
    val amount: Double,
    val installments: Int,
    val purpose: String?
)

data class ApplyLoanResponse(
    val success: Boolean,
    val message: String?,
    val loan: AppliedLoanDto
)

data class AppliedLoanDto(
    val id: String,
    val amount: Double,
    val processingFee: Double?,
    val netAmount: Double?,
    val installmentAmount: Double?,
    val installmentPlan: String?,
    val interestRate: Double?,
    val purpose: String?,
    val status: String,
    val startDate: String?,
    val endDate: String?,
    val nextPaymentDate: String?
)
