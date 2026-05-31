package ar.edu.ort.lendlyapp.data.remote.dto

data class TransactionDto(
    val id: String,
    val description: String,
    val amount: Double,
    val date: String,
    val type: String
)
