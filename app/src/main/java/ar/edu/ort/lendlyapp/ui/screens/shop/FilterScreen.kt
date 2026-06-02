package ar.edu.ort.lendlyapp.ui.screens.shop

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.OutlineButton
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BaseDark
import ar.edu.ort.lendlyapp.ui.theme.BorderNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent

private val brands = listOf("All", "Nike", "Adidas", "Puma", "Jordan")
private val genders = listOf("All", "Men", "Women")
private val sortOptions = listOf("Most Recent", "Popular", "Low Interest")
private val priceRanges = listOf("All", "$500 - $1000", "$1000 - $5000")

@Composable
fun FilterScreen(
    initial: ShopFilter,
    onClose: () -> Unit,
    onApply: (ShopFilter) -> Unit
) {
    var current by remember { mutableStateOf(initial) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = ContentPrimary
                )
            }
            Text(
                text = "Filter",
                style = MaterialTheme.typography.titleLarge,
                color = ContentPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            FilterGroup(
                label = "Brands",
                options = brands,
                selected = current.brand,
                onSelect = { current = current.copy(brand = it) }
            )
            FilterGroup(
                label = "Gender",
                options = genders,
                selected = current.gender,
                onSelect = { current = current.copy(gender = it) }
            )
            FilterGroup(
                label = "Sort by",
                options = sortOptions,
                selected = current.sortBy,
                onSelect = { current = current.copy(sortBy = it) }
            )
            FilterGroup(
                label = "Price Range",
                options = priceRanges,
                selected = current.priceRange,
                onSelect = { current = current.copy(priceRange = it) }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundElevated)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                OutlineButtonDark(
                    text = "Reset Filter",
                    onClick = { current = ShopFilter() }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                PrimaryButton(
                    text = "Apply",
                    onClick = { onApply(current) }
                )
            }
        }
    }
}

@Composable
private fun FilterGroup(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = ContentPrimary
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(options) { option ->
                FilterPill(
                    label = option,
                    selected = option == selected,
                    onClick = { onSelect(option) }
                )
            }
        }
    }
}

@Composable
private fun FilterPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) InteractiveAccent else BackgroundElevated
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(bg)
            .border(1.dp, BorderNeutral, CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = ContentPrimary
        )
    }
}

@Composable
private fun OutlineButtonDark(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(CircleShape)
            .border(1.dp, BaseDark, CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = ContentPrimary
        )
    }
}
