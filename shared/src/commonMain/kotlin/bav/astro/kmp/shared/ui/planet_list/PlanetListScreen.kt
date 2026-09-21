package bav.astro.kmp.shared.ui.planet_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
import astrokmp.shared.generated.resources.add_planet
import astrokmp.shared.generated.resources.no_entries
import bav.astro.kmp.shared.planets.Planet
import bav.astro.kmp.shared.planets.PlanetType
import org.jetbrains.compose.resources.stringResource

@Composable
fun PlanetListScreen(
    viewModel: PlanetListViewModel,
    onAddPlanetClick: () -> Unit,
    onEditPlanetClick: (Planet) -> Unit,
    modifier: Modifier = Modifier
) {
    val planets by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPlanetClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(Res.string.add_planet)
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
            if (planets.isEmpty()) {
                Text(
                    text = stringResource(Res.string.no_entries),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(planets) { person ->
                        PlanetRow(
                            planet = person,
                            onEditPlanetClick = onEditPlanetClick,
                            onSwitchVisibilityClick = viewModel::switchPlanetVisibility,
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetRow(
    planet: Planet,
    onEditPlanetClick: (Planet) -> Unit,
    onSwitchVisibilityClick: (Planet) -> Unit,
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
                text = planet.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = planet.period.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row {
            Button(
                onClick = { onSwitchVisibilityClick(planet) }
            ) {
                Icon(
                    imageVector = if (planet.isVisible)
                        Icons.Default.CheckCircle
                    else
                        Icons.Default.Close,
                    contentDescription = null
                )
            }
            if (planet.type == PlanetType.CUSTOM) {
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    onClick = { onEditPlanetClick(planet) }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }
            }
        }
    }
}
