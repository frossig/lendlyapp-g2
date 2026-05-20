package com.example.simulacro.data.repository

import com.example.simulacro.data.remote.ApiService
import com.example.simulacro.data.remote.ItemDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Capa intermedia entre los ViewModels y las fuentes de datos
 * (API REST vía Retrofit, Firestore, cache local, etc.).
 *
 * El ViewModel NO conoce a Retrofit ni a Firestore: pide al repositorio
 * y el repositorio decide de dónde sacar la data. Eso permite cambiar
 * el origen sin tocar la UI.
 */
@Singleton
class ItemRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getItems(): List<ItemDto> = api.getItems()

    suspend fun getItem(id: String): ItemDto = api.getItem(id)
}
