package bav.astro.kmp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import bav.astro.kmp.shared.navigation.BottomSheetSceneStrategy
import bav.astro.kmp.shared.navigation.NavKey
import bav.astro.kmp.shared.ui.add_person.AddPersonScreen
import bav.astro.kmp.shared.ui.add_person.AddPersonViewModel
import bav.astro.kmp.shared.ui.person_list.PersonListScreen
import bav.astro.kmp.shared.ui.person_list.PersonListViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = remember { mutableStateListOf<NavKey>(NavKey.PersonList) }
        val bottomSheetStrategy = remember { BottomSheetSceneStrategy<NavKey>() }

        NavDisplay(
            backStack = backStack,
            onBack = { 
                if (backStack.size > 1) {
                    backStack.removeLastOrNull() 
                }
            },
            sceneStrategies = listOf(bottomSheetStrategy),
            entryProvider = entryProvider {
                entry<NavKey.PersonList> {
                    val viewModel = koinViewModel<PersonListViewModel>()
                    PersonListScreen(
                        viewModel = viewModel,
                        onAddPersonClick = { backStack.add(NavKey.AddPerson) }
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
            }
        )
    }
}
