package com.imposter.app.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imposter.app.game.GameType
import com.imposter.app.game.RoleAssignment

private enum class Phase { PASS, REVEAL, STARTING }

@Composable
fun RevealScreen(
    viewModel: GameViewModel,
    onFinished: () -> Unit,
) {
    val result = viewModel.roundResult
    if (result == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Keine Runde aktiv.")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onFinished) { Text("Zurück") }
        }
        return
    }

    val assignments = result.assignments
    var index by remember { mutableIntStateOf(0) }
    var phase by remember { mutableStateOf(Phase.PASS) }

    val assignment = assignments[index]
    val isLast = index == assignments.lastIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (phase != Phase.STARTING) {
            Text(
                "Spieler ${index + 1} von ${assignments.size}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.height(16.dp))
        }

        Crossfade(targetState = phase, label = "reveal-phase") { current ->
            when (current) {
                Phase.PASS -> PassCard(
                    name = assignment.player.name,
                    onReady = { phase = Phase.REVEAL },
                )
                Phase.REVEAL -> RevealCard(
                    assignment = assignment,
                    gameType = result.gameType,
                    topicName = result.topicName,
                    specialSeesCategory = viewModel.specialSeesCategory,
                    isLast = isLast,
                    onNext = {
                        if (isLast) {
                            phase = Phase.STARTING
                        } else {
                            index += 1
                            phase = Phase.PASS
                        }
                    },
                )
                Phase.STARTING -> StartingPlayerCard(
                    playerName = viewModel.startingPlayer?.name
                        ?: assignments.first().player.name,
                    onContinue = onFinished,
                )
            }
        }
    }
}

@Composable
private fun PassCard(name: String, onReady: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("📱➡️", fontSize = 44.sp)
                Spacer(Modifier.height(16.dp))
                Text("Gib das Gerät an", fontSize = 18.sp, color = Color.White)
                Text(
                    name,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onReady,
            modifier = Modifier.fillMaxWidth().height(56.dp),
        ) {
            Text("Ich bin $name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun RevealCard(
    assignment: RoleAssignment,
    gameType: GameType,
    topicName: String,
    specialSeesCategory: Boolean,
    isLast: Boolean,
    onNext: () -> Unit,
) {
    var revealed by remember(assignment.player.id) { mutableStateOf(false) }

    // Special role text differs per game. Undercover players always get a word (word != null).
    val specialTitle = when (gameType) {
        GameType.IMPOSTER -> "IMPOSTER"
        GameType.SPYFALL -> "SPION"
        GameType.UNDERCOVER -> "UNDERCOVER"
    }
    val wordLabel = if (gameType == GameType.SPYFALL) "Dein Ort" else "Dein Wort"

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(16.dp))
                .pointerInput(assignment.player.id) {
                    detectTapGestures(
                        onPress = {
                            revealed = true
                            try {
                                awaitRelease()
                            } finally {
                                revealed = false
                            }
                        },
                    )
                },
            colors = CardDefaults.cardColors(
                containerColor = if (revealed) {
                    if (assignment.word == null) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (!revealed) {
                    Text("🤫", fontSize = 52.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Gedrückt halten zum Aufdecken",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                } else if (assignment.word == null) {
                    // No word => special role (imposter / spy)
                    Text("🕵️", fontSize = 52.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(specialTitle, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    if (gameType == GameType.IMPOSTER && specialSeesCategory && topicName.isNotBlank()) {
                        Spacer(Modifier.height(8.dp))
                        Text("Kategorie: $topicName", fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Center)
                    }
                } else {
                    Text(wordLabel, fontSize = 16.sp, color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        assignment.word,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
        ) {
            Text(
                if (isLast) "Fertig – auflösen" else "Weiter",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun StartingPlayerCard(playerName: String, onContinue: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(32.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("🎲", fontSize = 52.sp)
                Spacer(Modifier.height(12.dp))
                Text(
                    "beginnt die Runde:",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    playerName,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(56.dp),
        ) {
            Text("Weiter zur Auflösung", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
