package com.damian.minios.domain

/**
 * AppState
 * 
 * Represents the internal state of the MiniOS application.
 * This can include global UI states, navigation history, or flags for special operating modes.
 * Located in the domain package as it defines the business/application logic state.
 */
data class AppState(
    val isMinimalistMode: Boolean = false,
    val isSetupComplete: Boolean = false,
    val currentTheme: ThemeMode = ThemeMode.SYSTEM
)

/**
 * ThemeMode
 * 
 * Defines how the app theme should behave.
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}
