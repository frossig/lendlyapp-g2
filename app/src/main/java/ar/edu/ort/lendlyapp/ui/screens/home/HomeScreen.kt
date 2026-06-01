package ar.edu.ort.lendlyapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import ar.edu.ort.lendlyapp.data.remote.dto.UserDto
import ar.edu.ort.lendlyapp.ui.components.LoanCard
import ar.edu.ort.lendlyapp.ui.components.ProductCard
import ar.edu.ort.lendlyapp.ui.components.formatPhp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BalanceText
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import ar.edu.ort.lendlyapp.ui.theme.SectionTitle

@Composable
fun HomeScreen(
    onCashIn: () -> Unit = {},
    onNotifications: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundElevated)
            .statusBarsPadding()
    ) {
        HomeHeader(onNotifications = onNotifications)

        when (val s = state) {
            HomeUiState.Loading -> LoadingState()
            is HomeUiState.Error -> ErrorState(message = s.message, onRetry = viewModel::load)
            is HomeUiState.Success -> HomeContent(state = s, onCashIn = onCashIn)
        }
    }
}

@Composable
private fun HomeHeader(onNotifications: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* TODO: ir a Manage */ }, modifier = Modifier.size(40.dp)) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Profile",
                tint = ContentPrimary
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.logo_lendly_small),
                contentDescription = "LendlyApp",
                modifier = Modifier.size(width = 70.dp, height = 26.dp)
            )
        }
        IconButton(onClick = onNotifications, modifier = Modifier.size(40.dp)) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = ContentPrimary
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = InteractiveAccent)
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = ContentSecondary
        )
        TextButton(onClick = onRetry) {
            Text("Reintentar", color = ContentLink)
        }
    }
}

@Composable
private fun HomeContent(state: HomeUiState.Success, onCashIn: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountSection(user = state.user, onCashIn = onCashIn)
        UnpaidLoansSection(loans = state.unpaidLoans)
        RecommendedSection(products = state.recommendedProducts)
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun AccountSection(user: UserDto, onCashIn: () -> Unit) {
    Text(
        text = "Account",
        style = MaterialTheme.typography.headlineLarge,
        color = ContentPrimary
    )
    BalanceCard(balance = user.availableBalance ?: 0.0, onCashIn = onCashIn)
}

@Composable
private fun BalanceCard(balance: Double, onCashIn: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundCream)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AVAILABLE BALANCE",
                style = MaterialTheme.typography.labelMedium,
                color = ContentSecondary,
                modifier = Modifier.weight(1f)
            )
            CashInPill(onClick = onCashIn)
        }
        Text(
            text = formatPhp(balance),
            style = BalanceText,
            color = ContentPrimary
        )
    }
}

@Composable
private fun CashInPill(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(InteractiveAccent)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            tint = ContentPrimary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.size(6.dp))
        Text(
            text = "Cash In",
            style = MaterialTheme.typography.labelLarge,
            color = ContentPrimary
        )
    }
}

@Composable
private fun UnpaidLoansSection(loans: List<LoanDto>) {
    SectionHeader(title = "Unpaid Loans", onSeeAll = { /* TODO: go to Loan tab */ })
    if (loans.isEmpty()) {
        Text(
            text = "No tenés préstamos pendientes.",
            style = MaterialTheme.typography.bodyMedium,
            color = ContentSecondary
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            loans.take(3).forEach { loan ->
                LoanCard(
                    lender = loan.lender,
                    lenderLogo = loan.lenderLogo,
                    amount = loan.amountDue ?: loan.amount,
                    nextPaymentLabel = loan.nextPaymentLabel
                )
            }
        }
    }
}

@Composable
private fun RecommendedSection(products: List<ProductDto>) {
    SectionHeader(title = "Recommended For You", onSeeAll = { /* TODO: go to Shop tab */ })
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(products) { product ->
            ProductCard(
                name = product.name,
                imageUrl = product.image,
                monthlyInstallment = product.monthlyInstallment,
                installmentMonths = product.installmentMonths
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = SectionTitle,
            color = ContentPrimary,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = onSeeAll) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "See All",
                    style = MaterialTheme.typography.labelLarge,
                    color = ContentLink
                )
                Spacer(Modifier.size(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = ContentLink,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
