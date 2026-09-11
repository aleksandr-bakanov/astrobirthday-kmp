package bav.astro.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import bav.astro.kmp.shared.navigation.NavKey
import bav.astro.kmp.shared.ui.PersonListScreen
import bav.astro.kmp.shared.ui.PersonListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = remember { mutableStateListOf<NavKey>(NavKey.PersonList) }

        NavDisplay(
            backStack = backStack,
            onBack = { 
                if (backStack.size > 1) {
                    backStack.removeLastOrNull() 
                }
            },
            entryProvider = entryProvider {
                entry<NavKey.PersonList> {
                    val viewModel = koinViewModel<PersonListViewModel>()
                    PersonListScreen(viewModel = viewModel)
                }
            }
        )
    }
}
