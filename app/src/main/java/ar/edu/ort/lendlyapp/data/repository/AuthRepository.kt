package ar.edu.ort.lendlyapp.data.repository

import ar.edu.ort.lendlyapp.data.local.SessionManager
import ar.edu.ort.lendlyapp.data.remote.ApiService
import ar.edu.ort.lendlyapp.data.remote.dto.CreateUserRequest
import ar.edu.ort.lendlyapp.data.remote.dto.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val session: SessionManager
) {

    suspend fun login(phone: String, password: String) {
        val res = api.login(LoginRequest(phone = phone, password = password))
        session.saveSession(token = res.token, userId = res.userId)
    }

    suspend fun register(request: CreateUserRequest) {
        val res = api.register(request)
        session.saveSession(token = res.token, userId = res.userId)
    }

    suspend fun logout() {
        session.clear()
    }
}
