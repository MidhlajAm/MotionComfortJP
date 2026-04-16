package com.example.myjp.service

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.example.myjp.data.Particle
import kotlin.random.Random

class FloatingParticlesView(context: Context) : View(context) {
    private val paint =
            Paint().apply {
                color = Color.argb(190, 127, 183, 242)
                isAntiAlias = true
            }
    private val particles = mutableListOf<Particle>()

    private var motionX = 0f
    private var motionY = 0f
    private var filteredX = 0f
    private var filteredY = 0f
    private var neutralX = 0f
    private var neutralY = 0f
    private var isNeutralInitialized = false

    private var speedMultiplier = 1.0f
    private var impactIntensity = 0f
    private var targetBubbleCount = 36
    private var cueType = 1

    fun setSpeed(value: Float) {
        speedMultiplier = 0.8f + (value * 2.0f)
    }

    fun setBubbleCount(value: Float) {
        targetBubbleCount = 10 + (value * 80).toInt()
        if (width > 0 && height > 0) {
            generateParticles(width, height)
        }
    }

    fun setBubbleColor(color: Int) {
        paint.color = color
        invalidate()
    }

    fun setCueType(type: Int) {
        cueType = type
        if (width > 0 && height > 0) {
            generateParticles(width, height)
        }
    }

    fun updateMotion(x: Float, y: Float) {
        if (width == 0 || height == 0) {
            return
        }

        // Apply a soft deadband filter to completely eliminate jitter from shaky hands
        val dx = x - filteredX
        val dy = y - filteredY
        val distance = kotlin.math.sqrt((dx * dx) + (dy * dy).toDouble()).toFloat()
        
        // Spike the impact intensity for Kinetic Dots based on absolute new movement
        impactIntensity += distance * 1.5f
        impactIntensity *= 0.85f // Fast decay

        val deadzone = 1.2f // Threshold intended to absorb shaky hand movements

        if (distance > deadzone) {
            // This prevents sudden jumps when transitioning out of the shaky zone
            val scale = (distance - deadzone) / distance
            filteredX += dx * scale * 0.1f
            filteredY += dy * scale * 0.1f
        }

        if (!isNeutralInitialized) {
            neutralX = filteredX
            neutralY = filteredY
            isNeutralInitialized = true
        } else {
            // Drift back to center very slowly so tilt makes strong permanent effect
            neutralX += (filteredX - neutralX) * 0.002f
            neutralY += (filteredY - neutralY) * 0.002f
        }

        val deltaX = (filteredX - neutralX)
        val deltaY = (filteredY - neutralY)

        val maxOffsetX = width * 0.5f
        val maxOffsetY = height * 0.5f

        // speedMultiplier makes tilt cause much more motion
        val scaledX = (deltaX * 1.5f * speedMultiplier / 5.5f).coerceIn(-1.5f, 1.5f)
        val scaledY = (deltaY * 1.5f * speedMultiplier / 5.5f).coerceIn(-1.5f, 1.5f)

        val targetX = -scaledX * maxOffsetX
        val targetY = scaledY * maxOffsetY

        // Smooth out interpolation further
        motionX += (targetX - motionX) * 0.15f
        motionY += (targetY - motionY) * 0.15f
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0 || h == 0) return
        generateParticles(w, h)
    }

    private fun generateParticles(w: Int, h: Int) {
        particles.clear()
        val count = if (cueType == 3) targetBubbleCount / 2 else targetBubbleCount
        repeat(count) {
            val depth = Random.nextFloat().coerceAtLeast(0.2f)
            particles.add(
                    Particle(
                            baseX = Random.nextFloat() * w,
                            baseY = Random.nextFloat() * h,
                            radius =
                                    if (cueType == 3) 25f + (Random.nextFloat() * 35f)
                                    else 6f + (Random.nextFloat() * 28f),
                            depth = depth
                    )
            )
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (cueType == 2) {
            // Steady Horizon lines
            paint.alpha = 130
            for (i in -2..4) {
                val y =
                        wrap(
                                (height / 2f) + (i * height * 0.18f) + (motionY * 0.6f),
                                height.toFloat()
                        )
                canvas.drawLine(0f, y, width.toFloat(), y, paint.apply { strokeWidth = 8f })
            }
        }

        for (particle in particles) {
            val parallax = 0.45f + (particle.depth * 0.9f)
            val drawX = wrap(particle.baseX + (motionX * parallax), width.toFloat())
            val drawY = wrap(particle.baseY + (motionY * parallax), height.toFloat())

            if (cueType == 3) {
                // Pulsating Rings
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 5f
                paint.alpha = (50 + (particle.depth * 100)).toInt().coerceIn(40, 150)
                canvas.drawCircle(drawX, drawY, particle.radius, paint)
            } else if (cueType == 4) {
                // Kinetic Dots (Dynamic size expanding upon sudden movement/impact)
                val pulse = impactIntensity.coerceIn(0f, 40f) * (0.3f + particle.depth * 0.7f)
                
                // Solid center dot
                paint.style = Paint.Style.FILL
                paint.alpha = (140 + (particle.depth * 100)).toInt().coerceIn(70, 240)
                canvas.drawCircle(drawX, drawY, particle.radius * 0.65f, paint)

                // Dynamic outer impact ring
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 4f
                paint.alpha = (70 + (particle.depth * 60)).toInt().coerceIn(40, 160)
                canvas.drawCircle(drawX, drawY, (particle.radius * 0.65f) + 8f + pulse, paint)
            } else {
                // Normal Particles (Type 1 and 2 background)
                paint.style = Paint.Style.FILL
                paint.alpha = (90 + (particle.depth * 120)).toInt().coerceIn(70, 210)
                canvas.drawCircle(drawX, drawY, particle.radius, paint)
            }
        }
    }

    private fun wrap(value: Float, limit: Float): Float {
        if (limit <= 0f) return value
        var wrapped = value % limit
        if (wrapped < 0f) wrapped += limit
        return wrapped
    }
}
