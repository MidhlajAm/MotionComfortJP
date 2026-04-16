package com.example.myjp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myjp.ui.theme.primaryBlueShaded

@Preview(showBackground = true)
@Composable
fun HomePage(
        darkModeEnabled: Boolean = false,
        onDarkModeChange: (Boolean) -> Unit = {},
        viewModel: HomeViewModel = viewModel()
) {

        val uiState = viewModel.uiState.collectAsState()
        val context = LocalContext.current
        val bubbleOptions =
                listOf(
                        Color(0xFF7FB7F2),
                        Color(0xFF7ED7C1),
                        Color(0xFFFFC857),
                        Color(0xFFFF8A6B),
                        Color(0xFFF4F7FB)
                )

        Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {
                        Row(
                                modifier = Modifier.fillMaxWidth().height(70.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                        ) {
                                Text(
                                        text = "Control Dashboard",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.padding(top = 14.dp),
                                )
                        }
                },
        ) { padding ->
                Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp)
                ) {
                        Surface(
                                shape = RoundedCornerShape(28.dp),
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                Column(
                                        modifier = Modifier.fillMaxWidth().padding(18.dp),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                Column {
                                                        Text(
                                                                text = "Enable motion sync",
                                                                fontWeight = FontWeight.Bold,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurface
                                                        )
                                                        Text(
                                                                text =
                                                                        "Live bubbles that follow\nvehicle motion",
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant,
                                                                fontSize = 13.sp
                                                        )
                                                }
                                                Switch(
                                                        checked = uiState.value.enableMotion,
                                                        onCheckedChange = {
                                                                viewModel.switchMotion(
                                                                        context = context
                                                                )
                                                        },
                                                        modifier = Modifier.scale(1.15f)
                                                )
                                        }

                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                Column {
                                                        Text(
                                                                text = "Dark mode",
                                                                fontWeight = FontWeight.Bold,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurface
                                                        )
                                                        Text(
                                                                text =
                                                                        "Use a calmer night-friendly\ndashboard",
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant,
                                                                fontSize = 13.sp
                                                        )
                                                }
                                                Switch(
                                                        checked = darkModeEnabled,
                                                        onCheckedChange = onDarkModeChange,
                                                        modifier = Modifier.scale(1.15f)
                                                )
                                        }

                                        SettingSlider(
                                                title = "Speed",
                                                value = uiState.value.speed,
                                                enabled = uiState.value.enableMotion,
                                                onValueChange = { newValue ->
                                                        viewModel.onSpeedChange(context, newValue)
                                                }
                                        )

                                        SettingSlider(
                                                title = "Bubble Amount",
                                                value = uiState.value.bubbleCount,
                                                enabled = uiState.value.enableMotion,
                                                onValueChange = { newValue ->
                                                        viewModel.onBubbleCountChange(
                                                                context,
                                                                newValue
                                                        )
                                                }
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Surface(
                                shape = RoundedCornerShape(28.dp),
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                Column(
                                        modifier = Modifier.fillMaxWidth().padding(18.dp),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                        Text(
                                                text = "Bubble color",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                bubbleOptions.forEach { bubbleColor ->
                                                        BubbleColorOption(
                                                                color = bubbleColor,
                                                                selected =
                                                                        uiState.value.bubbleColor ==
                                                                                bubbleColor,
                                                                onClick = {
                                                                        viewModel
                                                                                .onBubbleColorChange(
                                                                                        context,
                                                                                        bubbleColor
                                                                                )
                                                                }
                                                        )
                                                }
                                        }
                                }
                        }

                        Surface(
                                shape = RoundedCornerShape(28.dp),
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth().padding(top = 18.dp)
                        ) {
                                Column(
                                        modifier = Modifier.fillMaxWidth().padding(18.dp),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                        Text(
                                                text = "Cue Types",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                        )

                                        var expanded by remember { mutableStateOf(false) }
                                        val cues =
                                                listOf(
                                                        "Floating Particles",
                                                        "Steady Horizon",
                                                        "Pulsating Rings",
                                                        "Kinetic Dots"
                                                )
                                        val selectedCueIndex =
                                                (uiState.value.typeOfCue - 1).coerceIn(
                                                        0,
                                                        cues.size - 1
                                                )

                                        @OptIn(ExperimentalMaterial3Api::class)
                                        ExposedDropdownMenuBox(
                                                expanded = expanded,
                                                onExpandedChange = { expanded = !expanded }
                                        ) {
                                                OutlinedTextField(
                                                        readOnly = true,
                                                        shape = RoundedCornerShape(14.dp),
                                                        value = cues[selectedCueIndex],
                                                        onValueChange = {},
                                                        trailingIcon = {
                                                                ExposedDropdownMenuDefaults
                                                                        .TrailingIcon(
                                                                                expanded = expanded
                                                                        )
                                                        },
                                                        colors =
                                                                ExposedDropdownMenuDefaults
                                                                        .outlinedTextFieldColors(),
                                                        modifier =
                                                            Modifier.fillMaxWidth()
                                                                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                                                )
                                                ExposedDropdownMenu(
                                                        expanded = expanded,
                                                        onDismissRequest = { expanded = false },
                                                        shape = RoundedCornerShape(14.dp),
                                                    ) {
                                                        cues.forEachIndexed { index, selectionOption
                                                                ->
                                                                DropdownMenuItem(
                                                                        text = {
                                                                                Text(
                                                                                        text =
                                                                                                selectionOption
                                                                                )
                                                                        },
                                                                        onClick = {
                                                                                viewModel.changeCue(
                                                                                        context,
                                                                                        index + 1
                                                                                )
                                                                                expanded = false
                                                                        }
                                                                )
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
}

@Composable
private fun SettingSlider(
        title: String,
        value: Float,
        enabled: Boolean,
        onValueChange: (Float) -> Unit
) {
        Column {
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                ) {
                        Text(text = title, color = MaterialTheme.colorScheme.onSurface)
                        Text(
                                text = "${(value * 100).toInt()}%",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                }
                Slider(
                        value = value,
                        onValueChange = onValueChange,
                        enabled = enabled,
                        valueRange = 0f..1f,
                        colors =
                                SliderDefaults.colors(
                                        thumbColor = MaterialTheme.colorScheme.primary,
                                        activeTrackColor = MaterialTheme.colorScheme.primary,
                                        inactiveTrackColor = primaryBlueShaded,
                                ),
                )
        }
}

@Composable
private fun BubbleColorOption(color: Color, selected: Boolean, onClick: () -> Unit) {
        Box(
                modifier =
                        Modifier.clickable(
                            onClick = { onClick()},
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ).size(42.dp)
                                .background(color = color, shape = CircleShape)
                                .border(
                                        width = if (selected) 3.dp else 1.dp,
                                        color =
                                                if (selected) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.outlineVariant,
                                        shape = CircleShape
                                )

        )
}
