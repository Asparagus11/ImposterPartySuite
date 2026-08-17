package com.imposter.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imposter.app.game.GameType
import com.imposter.app.game.RoundResult

@Composable
fun ResolveScreen(
    viewModel: GameViewModel,
    onNewRound: () -> Unit,
    onNewCategory: () -> Unit,
    onHome: () -> Unit,
) {
    val result = viewModel.roundResult
    var revealed by remember { mutableStateOf(false) }

    val question = when (result?.gameType) {
        GameType.SPYFALL -> "Wer ist der Spion?"
        GameType.UNDERCOVER -> "Wer ist Undercover?"
        else -> "Wer ist der Imposter?"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("🗳️", fontSize = 60.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            question,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Diskutiert und stimmt ab. Wenn ihr bereit seid, löst auf.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(24.dp))

        if (!revealed) {
            Button(
                onClick = { revealed = true },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            ) {
                Text("Auflösen", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        AnimatedVisibility(visible = revealed && result != null) {
            if (result != null) ResolveCard(result)
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = onNewRound,
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Text("Neue Runde – gleiche Gruppe", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(10.dp))
        OutlinedButton(
            onClick = onNewCategory,
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Text("Einstellungen ändern", fontSize = 16.sp)
        }
        Spacer(Modifier.height(10.dp))
        OutlinedButton(
            onClick = onHome,
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Text("Zur Spielauswahl", fontSize = 16.sp)
        }
    }
}

@Composable
private fun ResolveCard(result: RoundResult) {
    val specialsLabel = specialsLabel(result.gameType, result.specials.size)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(
            Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (result.gameType) {
                GameType.UNDERCOVER -> {
                    LabeledWord("Bürger-Wort", result.secretWord)
                    Spacer(Modifier.height(10.dp))
                    LabeledWord("Undercover-Wort", result.altWord ?: "–")
                }
                GameType.SPYFALL -> {
                    LabeledWord("Der Ort war", result.secretWord)
                }
                GameType.IMPOSTER -> {
                    LabeledWord("Das Wort war", result.secretWord)
                    Text(
                        "${result.topicEmoji} ${result.topicName}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                specialsLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                "🕵️ " + result.specials.joinToString(", ") { it.name },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun LabeledWord(label: String, word: String) {
    Text(
        label,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(
        word,
        fontSize = 28.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
    )
}

private fun specialsLabel(type: GameType, count: Int): String = when (type) {
    GameType.IMPOSTER -> if (count == 1) "Der Imposter war" else "Die Imposter waren"
    GameType.SPYFALL -> if (count == 1) "Der Spion war" else "Die Spione waren"
    GameType.UNDERCOVER -> if (count == 1) "Der Undercover war" else "Die Undercover waren"
}
