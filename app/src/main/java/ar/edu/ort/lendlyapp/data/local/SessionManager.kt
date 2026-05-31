package ar.edu.ort.lendlyapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "lendly_session")

/**
 * Persistencia del token de sesión y user id usando DataStore (Preferences).
 *
 * Por qué DataStore y no SharedPreferences: es la API moderna de Google
 * (la anterior está deprecated), es asíncrona vía Flow y juega bien con
 * Coroutines.
 *
 * Por qué no Room: estamos guardando 2 claves sueltas; meter una tabla
 * sería matar moscas a cañonazos.
 */
@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val authToken: Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    val userId: Flow<String?> = context.dataStore.data.map { it[KEY_USER_ID] }

    suspend fun saveSession(token: String, userId: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_ID] = userId
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    /** Acceso sincrónico — útil para el AuthInterceptor que NO es suspend. */
    suspend fun currentToken(): String? = authToken.first()

    private companion object {
        val KEY_TOKEN = stringPreferencesKey("auth_token")
        val KEY_USER_ID = stringPreferencesKey("user_id")
    }
}
