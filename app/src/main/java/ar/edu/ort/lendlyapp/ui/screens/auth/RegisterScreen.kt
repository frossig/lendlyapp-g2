package ar.edu.ort.lendlyapp.ui.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.ui.components.AppTopBar
import ar.edu.ort.lendlyapp.ui.components.OtpInput
import ar.edu.ort.lendlyapp.ui.components.PersonalDetailsForm
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BaseDarkGreen
import ar.edu.ort.lendlyapp.ui.theme.BaseLight
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent

private val HPad = 24.dp

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    onGoToLogin: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    BackHandler {
        if (!viewModel.previousStep()) onBack()
    }

    val handleBack: () -> Unit = {
        if (!viewModel.previousStep()) onBack()
    }

    when (state.step) {
        RegisterStep.PHONE -> PhoneStep(state, viewModel, handleBack)
        RegisterStep.SMS -> SmsStep(state, viewModel, handleBack)
        RegisterStep.FACE -> FaceStep(handleBack, viewModel::nextStep)
        RegisterStep.ID -> IdStep(handleBack, viewModel::nextStep)
        RegisterStep.VERIFIED -> VerifiedStep(handleBack, viewModel::nextStep)
        RegisterStep.PROFILE -> ProfileStep(state, viewModel, handleBack)
        RegisterStep.SIGNATURE -> SignatureStep(handleBack, viewModel::nextStep)
        RegisterStep.PASSWORD -> PasswordStep(state, viewModel, handleBack)
        RegisterStep.DONE -> DoneStep(onDone = onRegistered)
    }
}

@Composable
private fun StepScaffold(
    onBack: () -> Unit,
    bottomSection: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        AppTopBar(onBack = onBack, onInfo = {})
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            content = content
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundElevated)
                .navigationBarsPadding()
                .padding(horizontal = HPad, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = bottomSection
        )
    }
}

@Composable
private fun StepHeader(
    title: String,
    subtitle: String? = null,
    centered: Boolean = false
) {
    val align = if (centered) TextAlign.Center else TextAlign.Start
    val modifierBase = Modifier.padding(horizontal = HPad).fillMaxWidth()

    Spacer(Modifier.height(8.dp))
    Text(
        text = title,
        style = MaterialTheme.typography.displayLarge,
        color = ContentPrimary,
        textAlign = align,
        modifier = modifierBase
    )
    if (subtitle != null) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = ContentSecondary,
            textAlign = align,
            modifier = modifierBase
        )
    }
    Spacer(Modifier.height(24.dp))
}

@Composable
private fun PhoneStep(
    state: RegisterUiState,
    vm: RegisterViewModel,
    onBack: () -> Unit
) {
    StepScaffold(
        onBack = onBack,
        bottomSection = {
            PrimaryButton(
                text = "Send Code",
                onClick = vm::nextStep,
                enabled = state.phoneValid
            )
        }
    ) {
        StepHeader(
            title = "Verify your phone number with a code",
            subtitle = "We will send you a One-Time-Password (OTP) to confirm you number."
        )
        Column(modifier = Modifier.padding(horizontal = HPad)) {
            Text(
                text = "Your Phone Number",
                style = MaterialTheme.typography.labelLarge,
                color = ContentSecondary
            )
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.countryCode,
                    onValueChange = vm::onCountryCodeChange,
                    singleLine = true,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = state.phone,
                    onValueChange = vm::onPhoneChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SmsStep(
    state: RegisterUiState,
    vm: RegisterViewModel,
    onBack: () -> Unit
) {
    val masked = remember(state.phone) {
        if (state.phone.length >= 3) "******" + state.phone.takeLast(3) else "******"
    }
    StepScaffold(
        onBack = onBack,
        bottomSection = {
            PrimaryButton(
                text = "Next",
                onClick = vm::nextStep,
                enabled = state.smsValid
            )
        }
    ) {
        StepHeader(
            title = "Enter the code",
            subtitle = "Enter the security code we sent to $masked"
        )
        Column(modifier = Modifier.padding(horizontal = HPad)) {
            Text(
                text = "Your Phone Number",
                style = MaterialTheme.typography.labelLarge,
                color = ContentSecondary
            )
            Spacer(Modifier.height(8.dp))
            OtpInput(value = state.smsCode, onValueChange = vm::onSmsCodeChange)
            Spacer(Modifier.height(20.dp))
            TextButton(onClick = { /* mock */ }) {
                Text(
                    text = "Didn't received a code?",
                    style = MaterialTheme.typography.labelLarge,
                    color = ContentLink,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
private fun FaceStep(onBack: () -> Unit, onNext: () -> Unit) {
    StepScaffold(
        onBack = onBack,
        bottomSection = { PrimaryButton(text = "Next", onClick = onNext) }
    ) {
        StepHeader(
            title = "Put your face in the frame",
            subtitle = "Follow these instruction, and let us get you onboarded."
        )
        Image(
            painter = painterResource(R.drawable.img_face_recognition),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(393f / 357f)
        )
    }
}

@Composable
private fun IdStep(onBack: () -> Unit, onNext: () -> Unit) {
    StepScaffold(
        onBack = onBack,
        bottomSection = { PrimaryButton(text = "Next", onClick = onNext) }
    ) {
        StepHeader(
            title = "Let's scan your ID",
            subtitle = "Always keep your phone in portrait mode, and here are some more tips."
        )
        Image(
            painter = painterResource(R.drawable.img_id_scan),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(393f / 357f)
        )
    }
}

@Composable
private fun VerifiedStep(onBack: () -> Unit, onNext: () -> Unit) {
    StepScaffold(
        onBack = onBack,
        bottomSection = { PrimaryButton(text = "Next", onClick = onNext) }
    ) {
        Spacer(Modifier.height(24.dp))
        Image(
            painter = painterResource(R.drawable.img_verified_shield),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(24.dp))
        StepHeader(
            title = "Woah, Your face and ID are the same!",
            subtitle = "We are just a few questions away from opening your own lendly loan account. Tap the button to continue.",
            centered = true
        )
        Box(
            modifier = Modifier
                .padding(horizontal = HPad)
                .fillMaxWidth()
                .background(BackgroundElevated, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Security Guard",
                    style = MaterialTheme.typography.titleLarge,
                    color = ContentPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Our online security feature world-class protection against hackers. It makes them cry and rethink their purpose in life.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ContentSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun ProfileStep(
    state: RegisterUiState,
    vm: RegisterViewModel,
    onBack: () -> Unit
) {
    StepScaffold(
        onBack = onBack,
        bottomSection = {
            PrimaryButton(text = "Next", onClick = vm::nextStep)
        }
    ) {
        StepHeader(title = "Enter your personal details")
        PersonalDetailsForm(
            state = state.toPersonalDetails(),
            onStateChange = vm::applyPersonalDetails,
            modifier = Modifier.padding(horizontal = HPad)
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SignatureStep(onBack: () -> Unit, onNext: () -> Unit) {
    StepScaffold(
        onBack = onBack,
        bottomSection = {
            Text(
                text = "By tapping \"Next\", you confirm that the information you provided is true and correct.",
                style = MaterialTheme.typography.bodySmall,
                color = ContentSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            PrimaryButton(text = "Next", onClick = onNext)
        }
    ) {
        StepHeader(
            title = "Let's seal the deal!",
            subtitle = "You can use your finger or a compatible stylus to write you signature"
        )
        Box(modifier = Modifier.padding(horizontal = HPad)) {
            Image(
                painter = painterResource(R.drawable.img_signature_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(393f / 357f)
                    .border(1.dp, ContentSecondary.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
private fun PasswordStep(
    state: RegisterUiState,
    vm: RegisterViewModel,
    onBack: () -> Unit
) {
    StepScaffold(
        onBack = onBack,
        bottomSection = {
            PrimaryButton(
                text = "Next",
                onClick = vm::submitRegistration,
                enabled = state.passwordValid,
                loading = state.loading
            )
        }
    ) {
        StepHeader(title = "Create your password")

        Column(modifier = Modifier.padding(horizontal = HPad)) {
            Text(
                text = "Choose a password",
                style = MaterialTheme.typography.labelLarge,
                color = ContentSecondary
            )
            Spacer(Modifier.height(8.dp))

            val visibilityIcon: ImageVector =
                if (state.showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                placeholder = { Text("********") },
                visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = vm::togglePasswordVisibility) {
                        Icon(imageVector = visibilityIcon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = "At least 9 characters, containing a letter and a number",
                style = MaterialTheme.typography.bodySmall,
                color = ContentSecondary
            )

            if (state.error != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun DoneStep(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaseDarkGreen)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            IconButton(
                onClick = onDone,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = BaseLight
                )
            }
            Image(
                painter = painterResource(R.drawable.logo_lendly_small),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 115.dp, height = 40.dp)
                    .align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(0.2f))

            Image(
                painter = painterResource(R.drawable.ic_check_success),
                contentDescription = null,
                modifier = Modifier.size(width = 183.dp, height = 330.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "ALL DONE!",
                style = MaterialTheme.typography.displayLarge,
                color = InteractiveAccent,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "You're ready to start a loan.",
                style = MaterialTheme.typography.bodyLarge,
                color = BaseLight,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(1f))

            PrimaryButton(text = "Done", onClick = onDone)

            Spacer(Modifier.height(8.dp))
        }
    }
}
