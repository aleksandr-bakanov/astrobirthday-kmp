package bav.astro.kmp.shared.ui.birthdays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import astrokmp.shared.generated.resources.Res
import astrokmp.shared.generated.resources.no_entries
import astrokmp.shared.generated.resources.years_amount
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BirthdaysScreen(
    viewModel: BirthdaysViewModel,
    modifier: Modifier = Modifier
) {
    val birthdays by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (birthdays.isEmpty()) {
                Text(
                    text = stringResource(Res.string.no_entries),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(birthdays) { person ->
                        BirthdayRow(person)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun BirthdayRow(data: PersonBirthdayData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = data.name,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = pluralStringResource(
                    Res.plurals.years_amount,
                    data.ageOnNextBirthday,
                    data.ageOnNextBirthday,
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(
            modifier = Modifier.padding(top = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = data.planetType.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = data.birthday.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
