package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.components.formatPhp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundNeutral
import ar.edu.ort.lendlyapp.ui.theme.BackgroundScreen
import ar.edu.ort.lendlyapp.ui.theme.BorderNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractivePrimary
import ar.edu.ort.lendlyapp.ui.theme.SentimentNegative

private data class InstallmentPlan(val months: Int, val interestRate: Double)

private val plans = listOf(
    InstallmentPlan(3, 1.99),
    InstallmentPlan(6, 2.99),
    InstallmentPlan(12, 3.99)
)

private val purposes = listOf(
    "Educational", "Personal", "Business", "Medical", "Home Improvement", "Other"
)

private const val MAX_AMOUNT = 30_000.0
private const val PROCESSING_FEE_RATE = 0.03

private fun monthlyFor(amount: Double, plan: InstallmentPlan): Double =
    if (plan.months == 0) 0.0 else amount * (1 + plan.interestRate / 100) / plan.months

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanFormScreen(
    viewModel: LoanViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val applyState by viewModel.applyState.collectAsState()

    // Resetea el estado de envio al entrar al formulario.
    LaunchedEffect(Unit) { viewModel.resetApply() }

    // Navega al exito cuando la solicitud se completa.
    LaunchedEffect(applyState) {
        if (applyState is ApplyLoanUiState.Success) onSuccess()
    }

    var amountText by remember { mutableStateOf("2000") }
    var selectedPlan by remember { mutableStateOf(plans[1]) }
    var purpose by remember { mutableStateOf<String?>(null) }
    var purposeExpanded by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }

    val amount = amountText.toDoubleOrNull()
    val amountValid = amount != null && amount > 0 && amount <= MAX_AMOUNT
    val purposeValid = purpose != null
    val formValid = amountValid && purposeValid

    val submitting = applyState is ApplyLoanUiState.Submitting

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
    ) {
        // Top bar con titulo centrado.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = ContentPrimary
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Info",
                        tint = ContentPrimary
                    )
                }
            }
            Text(
                text = "Loan",
                style = MaterialTheme.typography.titleMedium,
                color = ContentPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Please provide your details for your loan",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = ContentPrimary
                )
                Text(
                    text = "Please provide your details for your loan",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ContentSecondary
                )
            }

            // Step 1 - monto
            StepLabel("Step 1", "Enter loan amount")
            OutlinedTextField(
                value = amountText,
                onValueChange = { input -> amountText = input.filter { it.isDigit() || it == '.' } },
                prefix = { Text("\u20B1 ") },
                singleLine = true,
                isError = showErrors && !amountValid,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && !amountValid) {
                Text(
                    text = "Ingresa un monto valido (maximo ${formatPhp(MAX_AMOUNT)})",
                    style = MaterialTheme.typography.bodySmall,
                    color = SentimentNegative
                )
            }

            HorizontalDivider(color = BorderNeutral)

            // Step 2 - plan de cuotas
            StepLabel("Step 2", "Select an installment plan")
            plans.forEach { plan ->
                PlanRow(
                    plan = plan,
                    monthly = monthlyFor(amount ?: 0.0, plan),
                    selected = plan.months == selectedPlan.months,
                    onClick = { selectedPlan = plan }
                )
            }

            HorizontalDivider(color = BorderNeutral)

            // Step 3 - proposito
            StepLabel("Step 3", "Select your loan purpose")
            ExposedDropdownMenuBox(
                expanded = purposeExpanded,
                onExpandedChange = { purposeExpanded = !purposeExpanded }
            ) {
                OutlinedTextField(
                    value = purpose ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select a purpose") },
                    isError = showErrors && !purposeValid,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = purposeExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = purposeExpanded,
                    onDismissRequest = { purposeExpanded = false }
                ) {
                    purposes.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                purpose = option
                                purposeExpanded = false
                            }
                        )
                    }
                }
            }
            if (showErrors && !purposeValid) {
                Text(
                    text = "Selecciona el proposito del prestamo",
                    style = MaterialTheme.typography.bodySmall,
                    color = SentimentNegative
                )
            }

            // Summary
            SummaryCard(
                amount = amount ?: 0.0,
                processingFee = (amount ?: 0.0) * PROCESSING_FEE_RATE
            )

            if (applyState is ApplyLoanUiState.Error) {
                Text(
                    text = (applyState as ApplyLoanUiState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SentimentNegative,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(4.dp))
        }

        Column(modifier = Modifier.padding(16.dp)) {
            PrimaryButton(
                text = "Get This Loan",
                loading = submitting,
                onClick = {
                    showErrors = true
                    if (formValid && amount != null) {
                        viewModel.applyLoan(
                            amount = amount,
                            installments = selectedPlan.months,
                            purpose = purpose
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun StepLabel(step: String, title: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = step,
            style = MaterialTheme.typography.labelMedium,
            color = InteractivePrimary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = ContentPrimary
        )
    }
}

@Composable
private fun PlanRow(
    plan: InstallmentPlan,
    monthly: Double,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) BackgroundNeutral else BackgroundCream)
            .border(
                width = 1.dp,
                color = if (selected) InteractivePrimary else BorderNeutral,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${plan.months} Months",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = ContentPrimary
            )
            Text(
                text = "${plan.interestRate}% Interest",
                style = MaterialTheme.typography.bodySmall,
                color = ContentSecondary
            )
        }
        Text(
            text = "${formatPhp(monthly)}/mo",
            style = MaterialTheme.typography.titleMedium,
            color = ContentPrimary
        )
    }
}

@Composable
private fun SummaryCard(amount: Double, processingFee: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = ContentPrimary
        )
        SummaryRow("Loan Amount", formatPhp(amount))
        SummaryRow("${(PROCESSING_FEE_RATE * 100).toInt()}% Processing Fee", "-${formatPhp(processingFee)}")
        HorizontalDivider(color = BorderNeutral)
        SummaryRow(
            label = "Total amount to Receive",
            value = formatPhp(amount - processingFee),
            emphasize = true
        )
        SummaryRow("Lender", "\u2014")
        Text(
            text = "What is this?",
            style = MaterialTheme.typography.bodySmall,
            color = ContentLink
        )
    }
}

@Composable
private fun SummaryRow(label: String, value: String, emphasize: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (emphasize) ContentPrimary else ContentSecondary,
            fontWeight = if (emphasize) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = ContentPrimary,
            fontWeight = if (emphasize) FontWeight.Bold else FontWeight.Normal
        )
    }
}