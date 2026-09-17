package bav.astro.kmp.shared.ui.person_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import astrokmp.shared.generated.resources.add_person
import astrokmp.shared.generated.resources.no_entries
import bav.astro.kmp.shared.database.Person
import org.jetbrains.compose.resources.stringResource

@Composable
fun PersonListScreen(
    viewModel: PersonListViewModel,
    onAddPersonClick: () -> Unit,
    onEditPersonClick: (Person) -> Unit,
    modifier: Modifier = Modifier
) {
    val people by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPersonClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(Res.string.add_person)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (people.isEmpty()) {
                Text(
                    text = stringResource(Res.string.no_entries),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(people) { person ->
                        PersonRow(
                            person = person,
                            onEditPersonClick = onEditPersonClick,
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun PersonRow(
    person: Person,
    onEditPersonClick: (Person) -> Unit,
) {
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
                text = person.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = person.birthday,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(
            modifier = Modifier.padding(top = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = { onEditPersonClick(person) }
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}
