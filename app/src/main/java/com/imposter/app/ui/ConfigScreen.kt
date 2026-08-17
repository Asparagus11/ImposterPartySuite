package com.imposter.app.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imposter.app.game.Topic

@Composable
fun ConfigScreen(
    viewModel: GameViewModel,
    onStart: () -> Unit,
) {
    LaunchedEffect(viewModel.gameType) { viewModel.applySuggestedSpecialCount() }

    val canStart = viewModel.canStart()
    val showGrid = viewModel.usesCategorySelection()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Text(
            "${viewModel.gameType.emoji}  ${viewModel.gameType.displayName}",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(10.dp))

        if (showGrid) {
            SelectionModeCard(viewModel)
            Spacer(Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(viewModel.currentTopics()) { topic ->
                    TopicTile(
                        topic = topic,
                        selected = viewModel.isCategoryActive(topic.id),
                        dimmed = viewModel.allCategories,
                        onClick = { viewModel.onCategoryTap(topic) },
                    )
                }
            }
        } else {
            // Spyfall uses a fixed location pool — no grid.
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("🌐 Orte-Paket", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Es wird zufällig einer aus vielen Orten gezogen. " +
                                "Alle kennen den Ort – außer den Spionen.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        SpecialSettings(viewModel)

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { if (viewModel.deal()) onStart() },
            enabled = canStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                if (!canStart) "Kategorie wählen"
                else if (showGrid) "Spiel starten (${viewModel.activeCategoryCount})"
                else "Spiel starten",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun SelectionModeCard(viewModel: GameViewModel) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            SwitchRow(
                title = "Mehrere Kategorien",
                subtitle = "Mehrere Kategorien gleichzeitig aktivieren",
                checked = viewModel.multiSelect,
                enabled = !viewModel.allCategories,
                onCheckedChange = { viewModel.updateMultiSelect(it) },
            )
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f))
            SwitchRow(
                title = "Alle Kategorien",
                subtitle = "Wörter aus allen Kategorien mischen",
                checked = viewModel.allCategories,
                enabled = true,
                onCheckedChange = { viewModel.updateAllCategories(it) },
            )
        }
    }
}

@Composable
private fun SwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (enabled) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = checked, enabled = enabled, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun TopicTile(
    topic: Topic,
    selected: Boolean,
    dimmed: Boolean,
    onClick: () -> Unit,
) {
    val border = if (selected) BorderStroke(3.dp, MaterialTheme.colorScheme.secondary) else null
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = !dimmed, onClick = onClick),
        border = border,
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.secondaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(topic.emoji, fontSize = 28.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                topic.name,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun SpecialSettings(viewModel: GameViewModel) {
    val count = viewModel.specialCount
    val max = viewModel.maxSpecials()
    val suggested = viewModel.suggestedSpecials()

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(viewModel.specialCountLabel(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Vorschlag: $suggested · max. $max",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RoundStepper(
                        symbol = "−",
                        enabled = count > 1,
                        onClick = { viewModel.updateSpecialCount(count - 1) },
                    )
                    Text(
                        "$count",
                        modifier = Modifier.padding(horizontal = 18.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    RoundStepper(
                        symbol = "+",
                        enabled = count < max,
                        onClick = { viewModel.updateSpecialCount(count + 1) },
                    )
                }
            }

            if (viewModel.usesSeesCategory()) {
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(Modifier.weight(1f)) {
                        Text("Imposter sieht Kategorie", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(
                            if (viewModel.specialSeesCategory)
                                "Imposter erfährt die Kategorie (leichter)"
                            else
                                "Imposter erfährt gar nichts (klassisch)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Switch(
                        checked = viewModel.specialSeesCategory,
                        onCheckedChange = { viewModel.updateSpecialSeesCategory(it) },
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f))
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Zufällige Reihenfolge", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        if (viewModel.randomizeOrder)
                            "Aufdecken in zufälliger Reihenfolge"
                        else
                            "Aufdecken in der eingegebenen Reihenfolge",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Switch(
                    checked = viewModel.randomizeOrder,
                    onCheckedChange = { viewModel.updateRandomizeOrder(it) },
                )
            }
        }
    }
}

@Composable
private fun RoundStepper(symbol: String, enabled: Boolean, onClick: () -> Unit) {
    FilledIconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(42.dp),
        shape = CircleShape,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(symbol, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
