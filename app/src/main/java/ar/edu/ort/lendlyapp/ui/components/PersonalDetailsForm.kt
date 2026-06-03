package ar.edu.ort.lendlyapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary

data class PersonalDetailsState(
    val firstName: String = "",
    val lastName: String = "",
    val day: String = "",
    val month: String = "",
    val year: String = "",
    val address: String = "",
    val city: String = "",
    val postalCode: String = "",
    val countryCode: String = "+65",
    val phone: String = ""
)

@Composable
fun PersonalDetailsForm(
    state: PersonalDetailsState,
    onStateChange: (PersonalDetailsState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LabeledTextField(
            label = "Full legal first and middle name(s)",
            value = state.firstName,
            onValueChange = { onStateChange(state.copy(firstName = it)) },
            placeholder = "John D."
        )
        Spacer(Modifier.height(16.dp))

        LabeledTextField(
            label = "Full legal last name",
            value = state.lastName,
            onValueChange = { onStateChange(state.copy(lastName = it)) },
            placeholder = "Doe"
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Date of birth",
            style = MaterialTheme.typography.labelLarge,
            color = ContentSecondary
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DateInput("Day", state.day, { onStateChange(state.copy(day = it.filter(Char::isDigit))) }, Modifier.weight(1f))
            DateInput("Month", state.month, { onStateChange(state.copy(month = it.filter(Char::isDigit))) }, Modifier.weight(1f))
            DateInput("Year", state.year, { onStateChange(state.copy(year = it.filter(Char::isDigit))) }, Modifier.weight(1.4f))
        }
        Spacer(Modifier.height(16.dp))

        LabeledTextField(
            label = "Address",
            value = state.address,
            onValueChange = { onStateChange(state.copy(address = it)) },
            placeholder = "Somewhere IN BLOCK 12"
        )
        Spacer(Modifier.height(16.dp))

        LabeledTextField(
            label = "City",
            value = state.city,
            onValueChange = { onStateChange(state.copy(city = it)) },
            placeholder = "Davao City"
        )
        Spacer(Modifier.height(16.dp))

        LabeledTextField(
            label = "Postal Code",
            value = state.postalCode,
            onValueChange = { onStateChange(state.copy(postalCode = it)) },
            placeholder = "8000",
            keyboardType = KeyboardType.Number
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Phone Number",
            style = MaterialTheme.typography.labelLarge,
            color = ContentSecondary
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = state.countryCode,
                onValueChange = { onStateChange(state.copy(countryCode = it)) },
                singleLine = true,
                modifier = Modifier.width(80.dp)
            )
            OutlinedTextField(
                value = state.phone,
                onValueChange = { onStateChange(state.copy(phone = it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = ContentSecondary,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DateInput(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = ContentSecondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
