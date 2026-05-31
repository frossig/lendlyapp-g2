package ar.edu.ort.lendlyapp.ui.screens.shop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Sección Shop — Featured Items, Item Categories, Popular Brands, Recommended,
 * Best Sellers, Products, y Product detail.
 *
 * TODO (compañero): listar productos con /products y armar pantalla de detalle.
 * Cachear el catálogo en Room para que cargue rápido en re-aperturas.
 */
@Composable
fun ShopScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Shop (placeholder)")
    }
}
