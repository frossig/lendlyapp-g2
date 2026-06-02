package ar.edu.ort.lendlyapp.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.ui.components.AccordionSection
import ar.edu.ort.lendlyapp.ui.components.MintCircleIcon
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BackgroundNeutral
import ar.edu.ort.lendlyapp.ui.theme.BorderNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import ar.edu.ort.lendlyapp.ui.theme.InteractivePrimary
import ar.edu.ort.lendlyapp.ui.theme.SubsectionHeader

@Composable
fun ProductDetailScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundElevated)
            .statusBarsPadding()
    ) {
        ProductDetailTopBar(onBack = onBack, title = "Apple iPhone 12 Pro Max")

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            FeatureChipsBar()
            ProductImagePager()
            PriceHeader()
            MintSeparator()
            LocationSection()
            MintSeparator()
            MerchantsSection()
            MintSeparator()
            FeaturesAccordionSection()
            MintSeparator()
            SpecificationsAccordionSection()
            Spacer(Modifier.height(24.dp))
        }

        BottomActionBar(onContinue = { /* TODO */ })
    }
}

@Composable
private fun ProductDetailTopBar(onBack: () -> Unit, title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = ContentPrimary
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = ContentPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MintSeparator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(BackgroundNeutral)
    )
}

@Composable
private fun FeatureChipsBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(InteractiveAccent)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FeatureChip(icon = Icons.Outlined.SentimentSatisfied, text = "Low interest")
        FeatureChip(icon = Icons.Outlined.LocalOffer, text = "0% Installment")
        FeatureChip(icon = Icons.Outlined.Storefront, text = "Easy pick-up")
    }
}

@Composable
private fun FeatureChip(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = InteractivePrimary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = ContentPrimary
        )
    }
}

@Composable
private fun ProductImagePager() {
    // TODO: cuando estén los 4 assets del iPhone, volver a HorizontalPager + overlay dinámico.
    Image(
        painter = painterResource(R.drawable.img_iphone_product),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .aspectRatio(393f / 220f)
    )
}

@Composable
private fun PriceHeader() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(
            text = "From as low as",
            style = MaterialTheme.typography.bodyMedium,
            color = ContentSecondary
        )
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "$1,200",
                style = MaterialTheme.typography.headlineLarge,
                color = ContentPrimary
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "per month",
                style = MaterialTheme.typography.bodyMedium,
                color = ContentSecondary,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Apple iPhone 15 Pro Max 256GB, Rose Gold",
            style = SubsectionHeader.copy(fontWeight = FontWeight.Bold),
            color = ContentPrimary
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun LocationSection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Text(
            text = "WHERE DO YOU WANT TO SHOP?",
            style = MaterialTheme.typography.labelMedium,
            color = ContentSecondary
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(BackgroundCream)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = InteractivePrimary
            )
            Text(
                text = "Davao City, Davao del Sur",
                style = SubsectionHeader.copy(fontWeight = FontWeight.Bold),
                color = ContentPrimary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = null,
                tint = ContentSecondary
            )
        }
    }
}

private data class Merchant(
    val name: String,
    val logoInitials: String,
    val price: String,
    val totalPrice: String,
    val downpayment: String
)

private val merchants = listOf(
    Merchant("Power Max Center", "PMX", "From $1,200 × 12 months", "$1,800 total price", "65% Downpayment"),
    Merchant("The Loop", "TL", "From $1,200 × 12 months", "$1,800 total price", "65% Downpayment"),
    Merchant("I-Mac Center", "iMC", "From $1,200 × 12 months", "$1,800 total price", "65% Downpayment")
)

@Composable
private fun MerchantsSection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Text(
            text = "MARKETPLACE PARTNER MERCHANTS",
            style = MaterialTheme.typography.labelMedium,
            color = ContentSecondary
        )
        Spacer(Modifier.height(16.dp))
        merchants.forEachIndexed { index, merchant ->
            MerchantCard(merchant)
            if (index < merchants.lastIndex) Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MerchantCard(m: Merchant) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ContentPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = m.logoInitials,
                style = MaterialTheme.typography.labelMedium,
                color = BackgroundElevated
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = m.name,
                style = SubsectionHeader.copy(fontWeight = FontWeight.Bold),
                color = ContentPrimary
            )
            AvailabilityBadge(text = "Limited Availability")
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = m.price, style = MaterialTheme.typography.labelMedium, color = ContentSecondary)
                Text(text = m.totalPrice, style = MaterialTheme.typography.labelMedium, color = ContentSecondary)
                Text(text = m.downpayment, style = MaterialTheme.typography.labelMedium, color = ContentSecondary)
            }
        }
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowDown,
            contentDescription = null,
            tint = ContentSecondary
        )
    }
}

@Composable
private fun AvailabilityBadge(text: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(BackgroundNeutral)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = InteractivePrimary
        )
    }
}

@Composable
private fun FeaturesAccordionSection() {
    var expanded by remember { mutableStateOf(true) }
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        AccordionSection(title = "FEATURES", expanded = expanded, onToggle = { expanded = !expanded }) {
            FeatureItem(
                icon = Icons.Outlined.Description,
                title = "How To Apply For A Loan",
                body = "(1) Only 1 ID needed for the loan approval and,\n(2) Click on Continue to check if you are qualified"
            )
            Spacer(Modifier.height(16.dp))
            FeatureItem(
                icon = Icons.Outlined.Shield,
                title = "Disclaimer",
                body = "Estimated calculation only. Down Payment and other loan terms may vary."
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FeatureItem(icon: ImageVector, title: String, body: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MintCircleIcon {
            Icon(icon, null, tint = InteractivePrimary, modifier = Modifier.size(18.dp))
        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = title,
                style = SubsectionHeader.copy(fontWeight = FontWeight.Bold),
                color = ContentPrimary
            )
            Text(
                text = body,
                style = MaterialTheme.typography.labelMedium,
                color = ContentSecondary
            )
        }
    }
}

@Composable
private fun SpecificationsAccordionSection() {
    var expanded by remember { mutableStateOf(true) }
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        AccordionSection(title = "PRODUCT SPECIFICATIONS", expanded = expanded, onToggle = { expanded = !expanded }) {
            SpecGroup(
                title = "Chip",
                lines = listOf(
                    "A16 Bionic chip",
                    "6-core CPU with 2 performance and 4 efficiency cores",
                    "5-core GPU",
                    "16-core Neural Engine"
                )
            )
            Spacer(Modifier.height(20.dp))
            SpecGroup(
                title = "Camera",
                lines = listOf(
                    "12MP camera",
                    "ƒ/1.9 aperture",
                    "Autofocus with Focus Pixels",
                    "Retina Flash"
                )
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SpecGroup(title: String, lines: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            style = SubsectionHeader,
            color = ContentPrimary
        )
        lines.forEach { line ->
            Text(
                text = line,
                style = MaterialTheme.typography.labelMedium,
                color = ContentSecondary
            )
        }
    }
}

@Composable
private fun BottomActionBar(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundElevated)
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BorderNeutral)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "From as low as",
                    style = MaterialTheme.typography.bodySmall,
                    color = ContentSecondary
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$1,200",
                        style = MaterialTheme.typography.headlineMedium,
                        color = ContentPrimary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "per month",
                        style = MaterialTheme.typography.bodySmall,
                        color = ContentSecondary,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
            Box(modifier = Modifier.width(140.dp)) {
                PrimaryButton(text = "Continue", onClick = onContinue)
            }
        }
    }
}
