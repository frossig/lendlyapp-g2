package ar.edu.ort.lendlyapp.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.TransactionDto
import ar.edu.ort.lendlyapp.ui.components.MainTabHeader
import ar.edu.ort.lendlyapp.ui.components.PaidLoanRow
import ar.edu.ort.lendlyapp.ui.components.TransactionRow
import ar.edu.ort.lendlyapp.ui.components.formatPhp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.ContentTertiary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoryScreen(
    onNotifications: () -> Unit = {},
    onTransactionClick: (String) -> Unit = {},
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundElevated)
            .statusBarsPadding()
    ) {
        MainTabHeader(onNotifications = onNotifications)

        when {
            state.loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) { CircularProgressIndicator(color = InteractiveAccent) }

            state.error != null -> Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = state.error!!,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ContentSecondary
                )
            }

            else -> HistoryContent(
                state = state,
                onQueryChange = viewModel::onQueryChange,
                onFilterChange = viewModel::onFilterChange,
                onTransactionClick = onTransactionClick
            )
        }
    }
}

@Composable
private fun HistoryContent(
    state: HistoryUiState,
    onQueryChange: (String) -> Unit,
    onFilterChange: (HistoryFilter) -> Unit,
    onTransactionClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "History",
                style = MaterialTheme.typography.headlineLarge,
                color = ContentPrimary
            )
            Spacer(Modifier.height(16.dp))
            SearchField(value = state.query, onChange = onQueryChange)
            Spacer(Modifier.height(12.dp))
            FilterChipsRow(current = state.filter, onChange = onFilterChange)
            Spacer(Modifier.height(8.dp))
        }

        if (state.filteredTransactions.isNotEmpty()) {
            sectionHeader("Today")
            items(state.filteredTransactions) { tx ->
                TransactionItem(tx = tx, onClick = { onTransactionClick(tx.id) })
            }
        }

        if (state.paidLoans.isNotEmpty()) {
            sectionHeader("Recent Loans")
            items(state.paidLoans) { loan -> PaidLoanItem(loan) }
        }
    }
}

@Composable
private fun SearchField(value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text("Search...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = ContentSecondary
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun FilterChipsRow(
    current: HistoryFilter,
    onChange: (HistoryFilter) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(HistoryFilter.entries) { filter ->
            val selected = filter == current
            FilterChip(
                selected = selected,
                onClick = { onChange(filter) },
                label = { Text(filter.label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = InteractiveAccent,
                    selectedLabelColor = ContentPrimary,
                    containerColor = BackgroundElevated,
                    labelColor = ContentPrimary
                )
            )
        }
    }
}

private fun LazyListScope.sectionHeader(label: String) {
    item {
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = ContentSecondary,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ContentTertiary.copy(alpha = 0.2f))
        )
    }
}

@Composable
private fun TransactionItem(tx: TransactionDto, onClick: () -> Unit) {
    val time = remember(tx.date) { formatTime(tx.date) }
    val friendly = remember(tx.type) { friendlyTitle(tx.type) }
    val lender = remember(tx.title) { extractLender(tx.title) }
    TransactionRow(
        time = time,
        title = friendly,
        lender = lender,
        amount = formatPhp(kotlin.math.abs(tx.amount)),
        onClick = onClick
    )
}

@Composable
private fun PaidLoanItem(loan: LoanDto) {
    val date = remember(loan.endDate) { formatDate(loan.endDate.orEmpty()) }
    PaidLoanRow(
        date = date,
        title = loan.purpose ?: loan.lender,
        lender = loan.lender
    )
}

private fun formatTime(iso: String): String = try {
    OffsetDateTime.parse(iso).format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))
} catch (_: Throwable) {
    ""
}

private fun formatDate(iso: String): String = try {
    OffsetDateTime.parse("${iso}T00:00:00Z")
        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH))
} catch (_: Throwable) {
    iso
}

private fun friendlyTitle(type: String): String = when (type) {
    "LOAN_PAYMENT" -> "Paid this month"
    "CASH_IN" -> "Added"
    "LOAN_DISBURSEMENT" -> "Added"
    else -> type.lowercase().replaceFirstChar { it.uppercase() }
}

private fun extractLender(title: String): String {
    val parts = title.split("—")
    return parts.getOrNull(1)?.trim() ?: parts.first().trim()
}
