package ar.edu.ort.lendlyapp.ui.screens.cashin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.AppTopBar
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BalanceText
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary

@Composable
fun CashInAmountScreen(
    provider: String,
    onNext: (Double) -> Unit,
    onBack: () -> Unit
) {
    var amountText by remember { mutableStateOf("2500") }
    val amount = amountText.toDoubleOrNull() ?: 0.0
    val canSubmit = amount > 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        AppTopBar(onBack = onBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Cash-In Amount",
                style = MaterialTheme.typography.headlineLarge,
                color = ContentPrimary
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Balance: ₱0.00",
                style = MaterialTheme.typography.bodyMedium,
                color = ContentSecondary
            )
            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = amountText,
                    onValueChange = { new -> amountText = new.filter { it.isDigit() }.take(8) },
                    textStyle = BalanceText.copy(
                        color = ContentPrimary,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("₱", style = BalanceText, color = ContentPrimary)
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(ContentSecondary.copy(alpha = 0.2f))
            )
            Spacer(Modifier.height(12.dp))

            Text(
                text = "$provider's max limit is ₱10,000.00 per day",
                style = MaterialTheme.typography.bodyMedium,
                color = ContentSecondary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundElevated)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            PrimaryButton(
                text = "Next",
                onClick = { onNext(amount) },
                enabled = canSubmit
            )
        }
    }
}
