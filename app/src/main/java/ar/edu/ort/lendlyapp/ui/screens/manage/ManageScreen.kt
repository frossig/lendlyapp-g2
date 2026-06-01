package ar.edu.ort.lendlyapp.ui.screens.manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.lendlyapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO (compañero): Pay Loans, Credit Score (gauge), Profile, Personal info, Support, Notifications. Logout ya funciona.
@Composable
fun ManageScreen(
    onLogout: () -> Unit,
    viewModel: ManageViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Manage (placeholder)")
        Button(onClick = { viewModel.logout(onLogout) }) { Text("Cerrar sesión") }
    }
}

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun logout(after: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            after()
        }
    }
}
