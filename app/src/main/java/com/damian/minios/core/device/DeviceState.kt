package com.damian.minios.core.device

/**
 * DeviceState
 * 
 * Represents the current physical state of the device.
 * Used for displaying system info on the home screen/status bar.
 * This class is located in the core.device package as it relates to hardware state.
 */
data class DeviceState(
    val batteryLevel: Int = 100,
    val isCharging: Boolean = false,
    val currentTime: String = "00:00",
    val connectivity: ConnectivityState = ConnectivityState.DISCONNECTED
)

/**
 * ConnectivityState
 * 
 * Enum to represent different network connection states.
 */
enum class ConnectivityState {
    CONNECTED,
    DISCONNECTED,
    AIRPLANE_MODE
}
