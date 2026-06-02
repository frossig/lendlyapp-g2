package ar.edu.ort.lendlyapp.ui.screens.shop

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.data.remote.dto.BrandDto
import ar.edu.ort.lendlyapp.data.remote.dto.CategoryDto
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import ar.edu.ort.lendlyapp.ui.components.BrandCard
import ar.edu.ort.lendlyapp.ui.components.CategoryCard
import ar.edu.ort.lendlyapp.ui.components.MainTabHeader
import ar.edu.ort.lendlyapp.ui.components.ProductCard
import ar.edu.ort.lendlyapp.ui.components.PromoCarousel
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import ar.edu.ort.lendlyapp.ui.theme.SectionTitle

private val promoImages = listOf(
    R.drawable.img_promo_nike
)

@Composable
fun ShopScreen(
    onNotifications: () -> Unit = {},
    onProductClick: () -> Unit = {},
    viewModel: ShopViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showFilter by remember { mutableStateOf(false) }

    if (showFilter) {
        FilterScreen(
            initial = state.filter,
            onClose = { showFilter = false },
            onApply = { newFilter ->
                viewModel.setFilter(newFilter)
                showFilter = false
            }
        )
        return
    }

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
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(color = InteractiveAccent) }

            state.error != null -> Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(state.error!!, color = ContentSecondary)
            }

            state.data != null -> {
                val data = state.data!!
                ShopContent(
                    query = state.query,
                    onQueryChange = viewModel::onQueryChange,
                    onFilterClick = { showFilter = true },
                    onProductClick = onProductClick,
                    categories = data.categories,
                    brands = data.brands,
                    featured = data.featured,
                    bestSellers = data.bestSellers
                )
            }
        }
    }
}

@Composable
private fun ShopContent(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onProductClick: () -> Unit,
    categories: List<CategoryDto>,
    brands: List<BrandDto>,
    featured: List<ProductDto>,
    bestSellers: List<ProductDto>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            value = query,
            onChange = onQueryChange,
            onFilterClick = onFilterClick,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            PromoCarousel(images = promoImages)
        }

        Section(title = "Shop By Category") {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { cat ->
                    CategoryCard(name = cat.name, icon = cat.icon)
                }
            }
        }

        Section(title = "Popular Brands") {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(brands) { brand ->
                    BrandCard(name = brand.name, logoUrl = brand.logo)
                }
            }
        }

        Section(title = "Recommended For You") {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(featured) { product ->
                    ProductCard(
                        name = product.name,
                        imageUrl = product.image,
                        monthlyInstallment = product.monthlyInstallment,
                        installmentMonths = product.installmentMonths,
                        onClick = onProductClick
                    )
                }
            }
        }

        Section(title = "Best Sellers") {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bestSellers) { product ->
                    ProductCard(
                        name = product.name,
                        imageUrl = product.image,
                        monthlyInstallment = product.monthlyInstallment,
                        installmentMonths = product.installmentMonths,
                        onClick = onProductClick
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SearchBar(
    value: String,
    onChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // TODO: filtrar productos por el query — hoy el input solo guarda el texto en el estado.
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            placeholder = { Text("Search for product") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = ContentSecondary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(InteractiveAccent),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filter",
                    tint = ContentPrimary
                )
            }
        }
    }
}

@Composable
private fun Section(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = SectionTitle,
                color = ContentPrimary,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = { /* TODO: see all */ }) {
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
        content()
    }
}
