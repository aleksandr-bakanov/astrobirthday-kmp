package bav.astro.kmp.shared.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bav.astro.kmp.shared.database.Person

@Composable
fun PersonListScreen(
    viewModel: PersonListViewModel,
    modifier: Modifier = Modifier
) {
    val people by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (people.isEmpty()) {
            Text(
                text = "No entries",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(people) { person ->
                    PersonRow(person)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun PersonRow(person: Person) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = person.name,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = person.birthday,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
