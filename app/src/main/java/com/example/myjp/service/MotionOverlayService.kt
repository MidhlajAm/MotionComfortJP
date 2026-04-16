package com.example.myjp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.provider.Settings
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.example.myjp.R

class MotionOverlayService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: FloatingParticlesView
    private var registeredSensorType: Int = Sensor.TYPE_ACCELEROMETER

    override fun onCreate() {
        super.onCreate()

        if (!Settings.canDrawOverlays(this)) {
            stopSelf()
            return
        }

        startForegroundNotification()
        createOverlay()
        registerSensors()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            if (it.hasExtra("SPEED")) {
                val value = it.getFloatExtra("SPEED", 0.5f)
                overlayView.setSpeed(value)
            }
            if (it.hasExtra("BUBBLE_COUNT")) {
                val value = it.getFloatExtra("BUBBLE_COUNT", 0.2f)
                overlayView.setBubbleCount(value)
            }
            if (it.hasExtra("BUBBLE_COLOR")) {
                val value = it.getIntExtra("BUBBLE_COLOR", android.graphics.Color.WHITE)
                overlayView.setBubbleColor(value)
            }
            if (it.hasExtra("CUE_TYPE")) {
                val value = it.getIntExtra("CUE_TYPE", 1)
                overlayView.setCueType(value)
            }
        }
        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val scale = if (registeredSensorType == Sensor.TYPE_LINEAR_ACCELERATION) 7.2f else 2.4f
            val x = it.values[0] * scale
            val y = it.values[1] * scale

            overlayView.updateMotion(x, y)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onBind(p0: Intent): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayView)
        sensorManager.unregisterListener(this)
    }

    private fun startForegroundNotification() {
        val channelId = "Notification_channel"
        val channel = NotificationChannel(
            channelId,
            "MotionService",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Motion Service")
            .setContentText("")
            .setSmallIcon(R.drawable.motion)
            .build()

        startForeground(1, notification)
    }

    private fun createOverlay() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        overlayView = FloatingParticlesView(this)
        overlayView.setSpeed(0.75f)
        overlayView.setBubbleCount(0.55f)
        overlayView.setCueType(1)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )

        windowManager.addView(overlayView, params)
    }

    private fun registerSensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val motionSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
                ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                ?: return
        registeredSensorType = motionSensor.type

        sensorManager.registerListener(
            this,
            motionSensor,
            SensorManager.SENSOR_DELAY_GAME
        )
    }
}
