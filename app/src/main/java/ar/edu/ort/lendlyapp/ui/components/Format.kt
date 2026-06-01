package ar.edu.ort.lendlyapp.ui.components

import java.text.NumberFormat
import java.util.Locale

private val numberFormatter: NumberFormat = NumberFormat.getNumberInstance(Locale.US).apply {
    minimumFractionDigits = 2
    maximumFractionDigits = 2
}

fun formatPhp(amount: Double): String = "₱${numberFormatter.format(amount)}"
