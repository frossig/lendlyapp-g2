package ar.edu.ort.lendlyapp.data.remote

import ar.edu.ort.lendlyapp.data.local.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
            .addHeader("x-api-key", API_KEY)

        val token = runBlocking { sessionManager.currentToken() }
        if (!token.isNullOrBlank()) {
            builder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(builder.build())
    }

    private companion object {
        const val API_KEY = "123456789"
    }
}
