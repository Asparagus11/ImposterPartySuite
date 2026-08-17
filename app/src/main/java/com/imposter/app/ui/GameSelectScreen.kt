package com.imposter.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imposter.app.game.GameType

@Composable
fun GameSelectScreen(
    onPickGame: (GameType) -> Unit,
    lastCrash: String? = null,
    onClearCrash: () -> Unit = {},
) {
    var rulesFor by remember { mutableStateOf<GameType?>(null) }
    var showAbout by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (lastCrash != null) {
            CrashCard(lastCrash = lastCrash, onClear = onClearCrash)
            Spacer(Modifier.height(20.dp))
        }

        Spacer(Modifier.height(8.dp))
        Text("🎭", fontSize = 64.sp)
        Text(
            "Partyspiele",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            "Wähle ein Spiel",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(Modifier.height(28.dp))

        GameType.entries.forEach { type ->
            GameCard(
                type = type,
                onPlay = { onPickGame(type) },
                onRules = { rulesFor = type },
            )
            Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = { showAbout = true }) {
            Text("ℹ\uFE0F  Über die App")
        }
    }

    rulesFor?.let { type ->
        RulesDialog(type = type, onDismiss = { rulesFor = null })
    }
    if (showAbout) {
        AboutDialog(onDismiss = { showAbout = false })
    }
}

@Composable
private fun GameCard(type: GameType, onPlay: () -> Unit, onRules: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onPlay),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(type.emoji, fontSize = 40.sp)
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        type.displayName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        type.tagline,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onRules) {
                    Text("📖  Anleitung")
                }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = onPlay) {
                    Text("Spielen  ▶", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun RulesDialog(type: GameType, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Verstanden") }
        },
        title = { Text("${type.emoji}  ${type.displayName} – Anleitung", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                GameGuide.rules(type).forEach { line ->
                    Row(Modifier.padding(vertical = 4.dp)) {
                        Text("• ", fontWeight = FontWeight.Bold)
                        Text(line, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        },
    )
}

@Composable
private fun AboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Schließen") }
        },
        title = { Text("Über die App", fontWeight = FontWeight.Bold) },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    "Codename „${GameGuide.About.codename}“ · Version ${GameGuide.About.version}",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.height(8.dp))
                Text(GameGuide.About.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                Text("Verwendete Open-Source-Bibliotheken", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                GameGuide.About.libraries.forEach { (name, license) ->
                    Row(Modifier.padding(vertical = 3.dp)) {
                        Text("• ", fontWeight = FontWeight.Bold)
                        Column {
                            Text(name, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                license,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    "Alle Wortlisten sind kuratiert und offline. Keine Werbung, kein Tracking.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                )
            }
        },
    )
}

@Composable
fun CrashCard(lastCrash: String, onClear: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "Letzter Absturz",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                lastCrash,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            TextButton(onClick = onClear, modifier = Modifier.align(Alignment.End)) {
                Text("Verwerfen")
            }
        }
    }
}
