package ar.edu.ort.lendlyapp.ui.screens.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.ui.components.PersonalDetailsForm
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BorderNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent

@Composable
fun EditAccountDetailsScreen(
    onBack: () -> Unit,
    viewModel: EditAccountDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    if (state.saved) {
        SavedSuccessView(onDone = onBack)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .statusBarsPadding()
    ) {
        TopBar(onBack = onBack)

        when {
            state.loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(color = InteractiveAccent) }

            state.error != null -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(state.error!!, color = ContentSecondary)
            }

            else -> FormContent(
                form = state.form,
                onFormChange = viewModel::onFormChange,
                saving = state.saving,
                onSave = viewModel::save
            )
        }
    }
}

@Composable
private fun SavedSuccessView(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundElevated)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BackgroundCream)
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onDone, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close",
                        tint = ContentPrimary
                    )
                }
            }
            Image(
                painter = painterResource(R.drawable.logo_lendly_small),
                contentDescription = null,
                modifier = Modifier
                    .height(28.dp)
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
                color = ContentPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Your info was saved",
                style = MaterialTheme.typography.bodyLarge,
                color = ContentSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(1f))

            PrimaryButton(text = "Done", onClick = onDone)

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun TopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = ContentPrimary
            )
        }
    }
}

@Composable
private fun FormContent(
    form: ar.edu.ort.lendlyapp.ui.components.PersonalDetailsState,
    onFormChange: (ar.edu.ort.lendlyapp.ui.components.PersonalDetailsState) -> Unit,
    saving: Boolean,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Enter your personal details",
                style = MaterialTheme.typography.displayLarge,
                color = ContentPrimary
            )
            Spacer(Modifier.height(24.dp))
            PersonalDetailsForm(state = form, onStateChange = onFormChange)
            Spacer(Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundElevated)
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BorderNeutral)
            )
            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                PrimaryButton(text = "Save", onClick = onSave, loading = saving)
            }
        }
    }
}
