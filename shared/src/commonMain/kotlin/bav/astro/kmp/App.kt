package bav.astro.kmp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import astrokmp.shared.generated.resources.Res
import astrokmp.shared.generated.resources.birthdays
import astrokmp.shared.generated.resources.people
import astrokmp.shared.generated.resources.planets
import bav.astro.kmp.shared.navigation.BottomSheetSceneStrategy
import bav.astro.kmp.shared.navigation.NavKey
import bav.astro.kmp.shared.ui.add_person.AddPersonScreen
import bav.astro.kmp.shared.ui.add_person.AddPersonViewModel
import bav.astro.kmp.shared.ui.add_planet.AddPlanetScreen
import bav.astro.kmp.shared.ui.add_planet.AddPlanetViewModel
import bav.astro.kmp.shared.ui.birthdays.BirthdaysScreen
import bav.astro.kmp.shared.ui.birthdays.BirthdaysViewModel
import bav.astro.kmp.shared.ui.edit_person.EditPersonScreen
import bav.astro.kmp.shared.ui.edit_person.EditPersonViewModel
import bav.astro.kmp.shared.ui.edit_planet.EditPlanetScreen
import bav.astro.kmp.shared.ui.edit_planet.EditPlanetViewModel
import bav.astro.kmp.shared.ui.person_list.PersonListScreen
import bav.astro.kmp.shared.ui.person_list.PersonListViewModel
import bav.astro.kmp.shared.ui.planet_list.PlanetListScreen
import bav.astro.kmp.shared.ui.planet_list.PlanetListViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = remember { mutableStateListOf<NavKey>(NavKey.Birthdays) }
        val bottomSheetStrategy = remember { BottomSheetSceneStrategy<NavKey>() }

        Scaffold(
            bottomBar = {
                val currentRoot = backStack.firstOrNull()
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoot == NavKey.Birthdays,
                        onClick = {
                            if (currentRoot != NavKey.Birthdays) {
                                backStack.clear()
                                backStack.add(NavKey.Birthdays)
                            }
                        },
                        icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                        label = { Text(stringResource(Res.string.birthdays)) }
                    )
                    NavigationBarItem(
                        selected = currentRoot == NavKey.PersonList,
                        onClick = {
                            if (currentRoot != NavKey.PersonList) {
                                backStack.clear()
                                backStack.add(NavKey.PersonList)
                            }
                        },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text(stringResource(Res.string.people)) }
                    )
                    NavigationBarItem(
                        selected = currentRoot == NavKey.PlanetList,
                        onClick = {
                            if (currentRoot != NavKey.PlanetList) {
                                backStack.clear()
                                backStack.add(NavKey.PlanetList)
                            }
                        },
                        icon = { Icon(Icons.Default.Star, contentDescription = null) },
                        label = { Text(stringResource(Res.string.planets)) }
                    )
                }
            }
        ) { paddingValues ->
            NavDisplay(
                modifier = Modifier.padding(paddingValues),
                backStack = backStack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                onBack = {
                    if (backStack.size > 1) {
                        backStack.removeLastOrNull()
                    }
                },
                sceneStrategies = listOf(bottomSheetStrategy),
                entryProvider = entryProvider {
                    entry<NavKey.Birthdays> {
                        val viewModel = koinViewModel<BirthdaysViewModel>()
                        BirthdaysScreen(viewModel = viewModel)
                    }
                    entry<NavKey.PersonList> {
                        val viewModel = koinViewModel<PersonListViewModel>()
                        PersonListScreen(
                            viewModel = viewModel,
                            onAddPersonClick = { backStack.add(NavKey.AddPerson) },
                            onEditPersonClick = { person ->
                                backStack.add(
                                    NavKey.EditPerson(personId = person.id)
                                )
                            }
                        )
                    }
                    entry<NavKey.AddPerson>(
                        metadata = BottomSheetSceneStrategy.bottomSheet()
                    ) {
                        val viewModel = koinViewModel<AddPersonViewModel>()
                        AddPersonScreen(
                            viewModel = viewModel,
                            onDismiss = { backStack.removeLastOrNull() }
                        )
                    }
                    entry<NavKey.EditPerson> {
                        val viewModel = koinViewModel<EditPersonViewModel>(
                            parameters = {
                                parametersOf(it.personId)
                            }
                        )
                        EditPersonScreen(
                            viewModel = viewModel,
                            onDismiss = { backStack.removeLastOrNull() }
                        )
                    }
                    entry<NavKey.PlanetList> {
                        val viewModel = koinViewModel<PlanetListViewModel>()
                        PlanetListScreen(
                            viewModel = viewModel,
                            onAddPlanetClick = { backStack.add(NavKey.AddPlanet) },
                            onEditPlanetClick = { planet ->
                                backStack.add(
                                    NavKey.EditPlanet(planetId = planet.id)
                                )
                            }
                        )
                    }
                    entry<NavKey.AddPlanet>(
                        metadata = BottomSheetSceneStrategy.bottomSheet()
                    ) {
                        val viewModel = koinViewModel<AddPlanetViewModel>()
                        AddPlanetScreen(
                            viewModel = viewModel,
                            onDismiss = { backStack.removeLastOrNull() }
                        )
                    }
                    entry<NavKey.EditPlanet> {
                        val viewModel = koinViewModel<EditPlanetViewModel>(
                            parameters = {
                                parametersOf(it.planetId)
                            }
                        )
                        EditPlanetScreen(
                            viewModel = viewModel,
                            onDismiss = { backStack.removeLastOrNull() }
                        )
                    }
                }
            )
        }
    }
}
