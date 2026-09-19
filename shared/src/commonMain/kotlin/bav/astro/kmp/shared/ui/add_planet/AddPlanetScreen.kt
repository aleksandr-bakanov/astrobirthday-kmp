package bav.astro.kmp.shared.ui.add_planet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import astrokmp.shared.generated.resources.Res
import astrokmp.shared.generated.resources.add_planet
import astrokmp.shared.generated.resources.birthday_format
import astrokmp.shared.generated.resources.cancel
import astrokmp.shared.generated.resources.name
import astrokmp.shared.generated.resources.period_format
import astrokmp.shared.generated.resources.submit
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddPlanetScreen(
    viewModel: AddPlanetViewModel,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.add_planet),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text(stringResource(Res.string.name)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.period,
            onValueChange = { viewModel.onPeriodChange(it) },
            label = { Text(stringResource(Res.string.period_format)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("365.25") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.cancel))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { viewModel.submit(onSuccess = onDismiss) },
                enabled = !viewModel.isSubmitting
            ) {
                Text(stringResource(Res.string.submit))
            }
        }
    }
}
