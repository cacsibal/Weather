package wpics.weather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import wpics.weather.models.UnitSystem

/**
 * A selection row using FilterChips to toggle between Metric, Imperial, and Hybrid units.
 *
 * @param selectedSystem The currently active [UnitSystem].
 * @param onSystemSelected Callback triggered when a new system is chosen.
 */
@Composable
fun UnitSelector(
    selectedSystem: UnitSystem,
    onSystemSelected: (UnitSystem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UnitSystem.entries.forEach { system ->
            FilterChip(
                selected = selectedSystem == system,
                onClick = { onSystemSelected(system) },
                label = {
                    Text(
                        text = system.displayName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    ) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}