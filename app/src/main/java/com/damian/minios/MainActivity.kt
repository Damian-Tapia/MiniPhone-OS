package com.damian.minios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.damian.minios.core.navigation.ContactsDestination
import com.damian.minios.core.navigation.HomeDestination
import com.damian.minios.core.navigation.MessagesDestination
import com.damian.minios.core.navigation.MusicDestination
import com.damian.minios.core.navigation.PhoneDestination
import com.damian.minios.core.navigation.SettingsDestination
import com.damian.minios.core.ui.MiniOSTheme
import com.damian.minios.feature.contacts.ContactsScreen
import com.damian.minios.feature.home.HomeScreen
import com.damian.minios.feature.home.HomeViewModel
import com.damian.minios.feature.messages.MessagesScreen
import com.damian.minios.feature.music.MusicScreen
import com.damian.minios.feature.phone.PhoneScreen
import com.damian.minios.feature.settings.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniOSTheme {
                MiniPhoneApp()
            }
        }
    }
}

/**
 * EDUCATIONAL COMMENT: CENTRAL NAVIGATION AND COMPOSE NAVIGATION 3
 *
 * 1. Backstack Ownership:
 *    With Jetpack Navigation 3, the backstack is a snapshot-state-backed collection of custom keys
 *    (e.g., [MiniOSDestination]). We initialize it using [rememberNavBackStack(HomeDestination)],
 *    which handles state persistence automatically through configuration changes or process death.
 *
 * 2. NavDisplay:
 *    [NavDisplay] is the primary UI container provided by Navigation 3. It observes the backstack list,
 *    identifies the topmost key, and resolves that key to a [NavEntry] via the `entryProvider` lambda.
 *
 * 3. Scoped ViewModels:
 *    By providing `entryDecorators`, specifically [rememberSaveableStateHolderNavEntryDecorator] and
 *    [rememberViewModelStoreNavEntryDecorator], each destination on the backstack gets its own
 *    isolated lifecycle and `ViewModelStoreOwner`. When a screen calls `viewModel()`, that ViewModel
 *    is scoped to that specific backstack entry and is automatically cleared when the screen is popped.
 */
@Composable
fun MiniPhoneApp() {
    // Instantiate or remember the persistent back stack with the Home screen as the starting destination.
    val backStack = rememberNavBackStack(HomeDestination)

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = {
            // Simple back navigation strategy for small form factors: pop the last entry
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is HomeDestination -> NavEntry<NavKey>(key) {
                    // Obtain a HomeViewModel scoped to this entry or parent.
                    val homeViewModel: HomeViewModel = viewModel()
                    HomeScreen(
                        viewModel = homeViewModel,
                        onNavigate = { destination ->
                            backStack.add(destination)
                        }
                    )
                }
                is PhoneDestination -> NavEntry<NavKey>(key) {
                    PhoneScreen(onBack = { backStack.removeLastOrNull() })
                }
                is MessagesDestination -> NavEntry<NavKey>(key) {
                    MessagesScreen(onBack = { backStack.removeLastOrNull() })
                }
                is MusicDestination -> NavEntry<NavKey>(key) {
                    MusicScreen(onBack = { backStack.removeLastOrNull() })
                }
                is ContactsDestination -> NavEntry<NavKey>(key) {
                    ContactsScreen(onBack = { backStack.removeLastOrNull() })
                }
                is SettingsDestination -> NavEntry<NavKey>(key) {
                    SettingsScreen(onBack = { backStack.removeLastOrNull() })
                }
                else -> NavEntry<NavKey>(key) {
                    // Fallback for unexpected keys
                }
            }
        }
    )
}
