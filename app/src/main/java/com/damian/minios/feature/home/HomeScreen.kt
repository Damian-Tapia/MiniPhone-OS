package com.damian.minios.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Sms
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.minios.core.navigation.MiniOSDestination
import com.damian.minios.core.ui.MiniOSTheme

/**
 * EDUCATIONAL COMMENT: JETPACK COMPOSE UI PATTERNS FOR SMALL FORM FACTORS
 *
 * Designing for a 2.8-inch screen poses unique user experience and accessibility challenges:
 * 1. Large Touch Targets: According to Android and Material Design guidelines, the minimum touch target
 *    size must be 48.dp. However, on ultra-small screens, increasing targets to 56.dp-64.dp prevents accidental mis-taps.
 * 2. High Contrast and Large Typography: Small displays make tiny or low-contrast text completely unreadable.
 *    We use prominent typography styles (such as `bodyLarge` or `headlineMedium`) with strong font weights ([FontWeight.Bold]).
 * 3. Minimal Clutter: Extraneous decorative elements, complex multi-column grids, or dense layout rows
 *    should be avoided. A single-column list with large intuitive icons provides the cleanest UX.
 * 4. Unidirectional Data Flow (UDF): The screen receives state from the ViewModel (`uiState`) and emits
 *    interaction events (`onNavigate`) upwards, keeping the UI completely decoupled and declarative.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigate: (MiniOSDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    // Observe state from the ViewModel reactively. Whenever the StateFlow emits a new HomeUiState,
    // this composable will automatically recompose with the fresh data.
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onNavigate = onNavigate,
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onNavigate: (MiniOSDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "MiniOS Home",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.menuItems) { item ->
                HomeMenuRow(
                    item = item,
                    onClick = { onNavigate(item.destination) }
                )
            }
        }
    }
}

@Composable
fun HomeMenuRow(
    item: HomeMenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Select the appropriate Material Symbol icon vector based on iconName
    val iconVector: ImageVector = when (item.iconName) {
        "Phone" -> Icons.Rounded.Phone
        "Messages" -> Icons.Rounded.Sms
        "Music" -> Icons.Rounded.MusicNote
        "Contacts" -> Icons.Rounded.Contacts
        "Settings" -> Icons.Rounded.Settings
        else -> Icons.Rounded.Settings
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 64.dp) // Generous touch target height tailored for 2.8" display
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = "${item.title} Icon",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold // Bold for extra legibility
                )
            )
        }
    }
}

@Preview(name = "Home Screen Preview")
@Composable
fun HomeScreenPreview() {
    MiniOSTheme {
        HomeScreenContent(
            uiState = HomeUiState(),
            onNavigate = {}
        )
    }
}
