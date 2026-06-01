package ar.edu.ort.lendlyapp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.ui.components.InitialsAvatar
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary

@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit,
    onGoToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.success) {
        if (state.success) onLoggedIn()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.6f))

        Image(
            painter = painterResource(R.drawable.logo_lendly_icon),
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )

        Spacer(Modifier.weight(0.5f))

        if (state.showCard) {
            SavedUserCard(
                fullName = state.savedFullName.orEmpty(),
                phone = state.savedPhone.orEmpty(),
                onChange = viewModel::toggleEditing
            )
        } else {
            OutlinedTextField(
                value = state.phoneInput,
                onValueChange = viewModel::onPhoneChange,
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                enabled = !state.loading,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Password",
            style = MaterialTheme.typography.labelLarge,
            color = ContentSecondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = { Text("123123123") },
            visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            enabled = !state.loading,
            trailingIcon = {
                IconButton(onClick = viewModel::togglePasswordVisibility) {
                    Icon(
                        imageVector = if (state.showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = if (state.showPassword) "Ocultar password" else "Mostrar password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = { /* TODO: forgot password */ },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Forgot your password?",
                style = MaterialTheme.typography.labelLarge,
                color = ContentLink,
                textDecoration = TextDecoration.Underline
            )
        }

        if (state.error != null) {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        PrimaryButton(
            text = "Log In",
            onClick = viewModel::submit,
            enabled = state.canSubmit,
            loading = state.loading
        )

        TextButton(onClick = onGoToRegister) {
            Text(
                text = "Or sign up for free",
                style = MaterialTheme.typography.labelLarge,
                color = ContentLink,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun SavedUserCard(
    fullName: String,
    phone: String,
    onChange: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsAvatar(fullName = fullName, size = 48.dp)

        Spacer(Modifier.size(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = fullName,
                style = MaterialTheme.typography.titleMedium,
                color = ContentPrimary
            )
            Text(
                text = phone,
                style = MaterialTheme.typography.bodyMedium,
                color = ContentSecondary
            )
        }

        TextButton(onClick = onChange) {
            Text(
                text = "Change",
                style = MaterialTheme.typography.labelLarge,
                color = ContentLink,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
