package com.example.myjp.ui.screens.home

import androidx.compose.ui.graphics.Color
import com.example.myjp.ui.theme.primaryBlue

data class HomeUiState(
    val enableMotion : Boolean = false,
    val typeOfCue : Int = 1,
    val speed : Float = 0.45f,
    val bubbleCount : Float = 0.15f,
    val bubbleColor: Color = primaryBlue,
)
