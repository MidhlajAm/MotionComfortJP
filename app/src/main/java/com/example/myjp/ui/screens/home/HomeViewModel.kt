package com.example.myjp.ui.screens.home
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.example.myjp.service.MotionOverlayService
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.net.toUri
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun switchMotion(context : Context) {

        if (!Settings.canDrawOverlays(context)) {

            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                "package:${context.packageName}".toUri()
            )
            context.startActivity(intent)

            return
        }

        val newState = !_uiState.value.enableMotion

       _uiState.value = uiState.value.copy(
           enableMotion = !_uiState.value.enableMotion,
       )

        val intent = Intent(context, MotionOverlayService::class.java).apply {
            putExtra("SPEED",uiState.value.speed)
            putExtra("BUBBLE_COUNT", uiState.value.bubbleCount)
            putExtra("BUBBLE_COLOR", uiState.value.bubbleColor.toArgb())
            putExtra("CUE_TYPE", uiState.value.typeOfCue)
        }

        if (newState) {
            ContextCompat.startForegroundService(context, intent)
        }else{
            context.stopService(intent)
        }
    }

    fun changeCue(context: Context, cue: Int) {
       _uiState.value = uiState.value.copy(
           typeOfCue = cue
       )
        if (_uiState.value.enableMotion) {
            val intent = Intent(context, MotionOverlayService::class.java)
            intent.putExtra("CUE_TYPE", cue)
            context.startService(intent)
        }
    }

    fun onSpeedChange(context: Context, value: Float) {

        _uiState.value = _uiState.value.copy(
            speed = value
        )

        if (_uiState.value.enableMotion) {
            val intent = Intent(context, MotionOverlayService::class.java)
            intent.putExtra("SPEED", value)
            context.startService(intent)
        }
    }


    fun onBubbleCountChange(context: Context, value: Float) {
        _uiState.value = uiState.value.copy(
            bubbleCount = value
        )

        if (_uiState.value.enableMotion) {
            val intent = Intent(context, MotionOverlayService::class.java)
            intent.putExtra("BUBBLE_COUNT", value)
            context.startService(intent)
        }
    }

    fun onBubbleColorChange(context: Context, color: Color) {
        _uiState.value = uiState.value.copy(
            bubbleColor = color
        )

        if (_uiState.value.enableMotion) {
            val intent = Intent(context, MotionOverlayService::class.java)
            intent.putExtra("BUBBLE_COLOR", color.toArgb())
            context.startService(intent)
        }
    }
}
