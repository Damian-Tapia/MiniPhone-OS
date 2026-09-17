package com.damian.minios.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * EDUCATIONAL COMMENT: MODERN ANDROID NAVIGATION WITH NAVIGATION 3
 *
 * In modern Android development, Jetpack Navigation 3 introduces a state-driven approach
 * to handling application screens. Unlike legacy Navigation 2.x which relied on a heavy
 * NavController and string-based routing, Navigation 3 treats the navigation backstack
 * as a simple, observable list of data objects called "Keys".
 *
 * Requirements for Navigation 3 keys:
 * 1. They must implement the [NavKey] marker interface so the library knows they represent
 *    a navigatable screen destination.
 * 2. They must be annotated with [@Serializable] from Kotlinx Serialization so that the
 *    backstack state can be automatically saved and restored during configuration changes
 *    (like screen rotation) or process death by the OS.
 *
 * Below we define a type-safe sealed hierarchy for all available screens in MiniPhone-OS.
 */
@Serializable
sealed interface MiniOSDestination : NavKey

@Serializable
data object HomeDestination : MiniOSDestination

@Serializable
data object PhoneDestination : MiniOSDestination

@Serializable
data object MessagesDestination : MiniOSDestination

@Serializable
data object MusicDestination : MiniOSDestination

@Serializable
data object ContactsDestination : MiniOSDestination

@Serializable
data object SettingsDestination : MiniOSDestination
