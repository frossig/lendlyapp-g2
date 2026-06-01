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

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val authToken: Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    val userId: Flow<String?> = context.dataStore.data.map { it[KEY_USER_ID] }
    val savedFullName: Flow<String?> = context.dataStore.data.map { it[KEY_FULL_NAME] }
    val savedPhone: Flow<String?> = context.dataStore.data.map { it[KEY_PHONE] }

    suspend fun saveSession(token: String, userId: String, fullName: String, phone: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_ID] = userId
            prefs[KEY_FULL_NAME] = fullName
            prefs[KEY_PHONE] = phone
        }
    }

    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
            prefs.remove(KEY_USER_ID)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun currentToken(): String? = authToken.first()

    private companion object {
        val KEY_TOKEN = stringPreferencesKey("auth_token")
        val KEY_USER_ID = stringPreferencesKey("user_id")
        val KEY_FULL_NAME = stringPreferencesKey("full_name")
        val KEY_PHONE = stringPreferencesKey("phone")
    }
}
