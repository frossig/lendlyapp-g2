package ar.edu.ort.lendlyapp.data.repository

import ar.edu.ort.lendlyapp.data.local.SessionManager
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val session: SessionManager
) {

    private suspend fun itemsCollection(): CollectionReference {
        val userId = session.userId.first() ?: error("No user id in session")
        return firestore.collection("favorites").document(userId).collection("items")
    }

    suspend fun getFavoriteIds(): Set<String> =
        itemsCollection().get().await().documents.map { it.id }.toSet()

    suspend fun getFavorites(): List<ProductDto> =
        itemsCollection().get().await().documents.map { it.toProductDto() }

    suspend fun addFavorite(product: ProductDto) {
        itemsCollection().document(product.id).set(product.toMap()).await()
    }

    suspend fun removeFavorite(productId: String) {
        itemsCollection().document(productId).delete().await()
    }
}

private fun ProductDto.toMap(): Map<String, Any?> = mapOf(
    "id" to id,
    "name" to name,
    "brand" to brand,
    "category" to category,
    "price" to price,
    "currency" to currency,
    "image" to image,
    "monthlyInstallment" to monthlyInstallment,
    "installmentMonths" to installmentMonths,
    "interestRate" to interestRate,
    "isFeatured" to isFeatured,
    "isAvailable" to isAvailable,
    "rating" to rating,
    "reviewCount" to reviewCount,
    "description" to description
)

private fun DocumentSnapshot.toProductDto(): ProductDto = ProductDto(
    id = getString("id") ?: id,
    name = getString("name").orEmpty(),
    brand = getString("brand").orEmpty(),
    category = getString("category"),
    price = getDouble("price") ?: 0.0,
    currency = getString("currency"),
    image = getString("image"),
    monthlyInstallment = getDouble("monthlyInstallment"),
    installmentMonths = getLong("installmentMonths")?.toInt(),
    interestRate = getDouble("interestRate"),
    isFeatured = getBoolean("isFeatured"),
    isAvailable = getBoolean("isAvailable"),
    rating = getDouble("rating"),
    reviewCount = getLong("reviewCount")?.toInt(),
    description = getString("description")
)
