package ar.edu.ort.lendlyapp.data.repository

import android.content.Context
import ar.edu.ort.lendlyapp.data.local.SessionManager
import ar.edu.ort.lendlyapp.data.remote.ApiService
import ar.edu.ort.lendlyapp.data.remote.GoogleAuthClient
import ar.edu.ort.lendlyapp.data.remote.dto.CreateUserRequest
import ar.edu.ort.lendlyapp.data.remote.dto.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val session: SessionManager,
    private val googleAuthClient: GoogleAuthClient
) {

    suspend fun login(phone: String, password: String) {
        val res = api.login(LoginRequest(phone = phone, password = password))
        session.saveSession(
            token = res.token,
            userId = res.user.id.toString(),
            fullName = res.user.fullName,
            phone = res.user.phone
        )
    }

    suspend fun register(request: CreateUserRequest) {
        val res = api.register(request)
        session.saveSession(
            token = res.token,
            userId = res.user.id.toString(),
            fullName = res.user.fullName,
            phone = res.user.phone
        )
    }

    suspend fun signInWithGoogle(activityContext: Context) {
        val user = googleAuthClient.signIn(activityContext)
        session.saveGoogleSession(
            token = user.id,
            userId = user.id,
            fullName = user.fullName,
            email = user.email
        )
    }

    suspend fun logout() {
        googleAuthClient.signOut()
        session.logout()
    }

    suspend fun forgetUser() {
        session.clearAll()
    }
}
