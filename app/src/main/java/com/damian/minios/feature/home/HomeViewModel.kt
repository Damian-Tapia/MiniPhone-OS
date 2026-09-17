package com.damian.minios.feature.home

import androidx.lifecycle.ViewModel
import com.damian.minios.core.navigation.MiniOSDestination
import com.damian.minios.core.navigation.PhoneDestination
import com.damian.minios.core.navigation.MessagesDestination
import com.damian.minios.core.navigation.MusicDestination
import com.damian.minios.core.navigation.ContactsDestination
import com.damian.minios.core.navigation.SettingsDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class representing an item on the Home screen menu list.
 */
data class HomeMenuItem(
    val title: String,
    val destination: MiniOSDestination,
    val iconName: String
)

/**
 * Data class representing the state of the Home UI.
 * Tailored for high visibility and clear presentation on a 2.8-inch display.
 */
data class HomeUiState(
    val menuItems: List<HomeMenuItem> = listOf(
        HomeMenuItem("Phone", PhoneDestination, "Phone"),
        HomeMenuItem("Messages", MessagesDestination, "Messages"),
        HomeMenuItem("Music", MusicDestination, "Music"),
        HomeMenuItem("Contacts", ContactsDestination, "Contacts"),
        HomeMenuItem("Settings", SettingsDestination, "Settings")
    ),
    val systemStatusText: String = "System Ready"
)

/**
 * EDUCATIONAL COMMENT: MODERN ANDROID ARCHITECTURE, VIEWMODELS, AND STATEFLOW
 *
 * 1. Architecture Components (MVVM / MVI):
 *    In modern Android app architecture, we separate concerns by dividing the codebase into layers.
 *    The UI Layer contains Composables that render information on the screen, while the Data Layer
 *    manages business logic and data sources. The ViewModel acts as a bridge between them.
 *
 * 2. ViewModel:
 *    A [ViewModel] is a special lifecycle-aware class designed to store and manage UI-related data.
 *    Crucially, ViewModels survive configuration changes (such as device rotations or system font size alterations).
 *    This ensures that data is cached and not re-fetched or lost when the Activity is recreated.
 *
 * 3. StateFlow:
 *    [StateFlow] is a state-holder observable flow that emits the current and new state updates to its collectors.
 *    - It always maintains a single latest value.
 *    - It is hot, meaning it exists independently of whether there are active subscribers or collectors.
 *    - By exposing data as a read-only StateFlow (`asStateFlow()`), the UI can safely collect state updates
 *      using lifecycle-aware operators (like `collectAsStateWithLifecycle()`), ensuring no memory leaks occur
 *      and the UI remains completely reactive.
 */
class HomeViewModel : ViewModel() {

    // MutableStateFlow is internal to the ViewModel, allowing safe updates within this class.
    private val _uiState = MutableStateFlow(HomeUiState())

    // Expose a read-only StateFlow to the UI layer to enforce unidirectional data flow (UDF).
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Function to update system status or respond to interactions.
     */
    fun updateSystemStatus(status: String) {
        _uiState.value = _uiState.value.copy(systemStatusText = status)
    }
}
