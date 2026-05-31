package ar.edu.ort.lendlyapp.data.remote.dto

data class TransactionsResponse(
    val success: Boolean,
    val pagination: PaginationDto?,
    val transactions: List<TransactionDto>
)

data class TransactionDto(
    val id: String,
    val type: String,
    val title: String,
    val description: String?,
    val amount: Double,
    val currency: String?,
    val status: String,
    val date: String,
    val loanId: String?,
    val referenceNumber: String?
)

data class PaginationDto(
    val page: Int,
    val limit: Int,
    val total: Int,
    val hasNextPage: Boolean
)
