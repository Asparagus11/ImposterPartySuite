package com.imposter.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imposter.app.game.GameEngine

@Composable
fun GroupSetupScreen(
    viewModel: GameViewModel,
    onContinue: () -> Unit,
) {
    val count = viewModel.playerCount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            "Wer spielt mit?",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "3 bis 15 Personen. Vergib Namen oder Kürzel.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(20.dp))

        // Player count stepper
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Spieler", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StepperButton(
                        symbol = "−",
                        enabled = count > GameEngine.MIN_PLAYERS,
                        onClick = { viewModel.setPlayerCount(count - 1) },
                    )
                    Text(
                        "$count",
                        modifier = Modifier.padding(horizontal = 20.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    StepperButton(
                        symbol = "+",
                        enabled = count < GameEngine.MAX_PLAYERS,
                        onClick = { viewModel.setPlayerCount(count + 1) },
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            itemsIndexed(viewModel.names) { index, name ->
                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.updateName(index, it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Spieler ${index + 1}") },
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                viewModel.persistGroup()
                onContinue()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text("Weiter", fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun StepperButton(symbol: String, enabled: Boolean, onClick: () -> Unit) {
    FilledIconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(44.dp),
        shape = CircleShape,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(symbol, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}
