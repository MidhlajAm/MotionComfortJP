<table style="border:none; border-collapse:collapse;">
  <tr>
    <td style="border:none; padding:0; vertical-align:middle;">
      <img src="https://res.cloudinary.com/dezwll9jv/image/upload/v1776359812/motion_comfort_app_icon_pcekfg.png"
           width="58"
           style="border-radius:12px;">
    </td>
    <td style="border:none; padding-left:10px; vertical-align:middle;">
      <h1 style="margin:0;">MotionComfort</h1>
    </td>
  </tr>
</table>

![Platform](https://img.shields.io/badge/Platform-Android-green)
![Language](https://img.shields.io/badge/Kotlin-100%25-blue)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-purple)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange)

---

**MotionComfort** is an Android application designed to reduce motion sickness while using a smartphone in moving vehicles such as cars, buses, or trains.

It uses real-time sensor data to generate synchronized visual cues (like floating particles and horizon lines) that align with vehicle motion, helping reduce sensory conflict and improve comfort.

---

## ✨ Features

* 🚘 Real-time motion detection using accelerometer
* 🎯 Motion-synchronized visual cues (Floating Particles, Horizon, etc.)
* 🎛 Adjustable sensitivity and intensity controls
* 🎨 Customizable particle colors
* 🧠 Multiple cue types (Kinetic Dots, Steady Horizon, Pulsating Rings)
* 📱 Overlay mode (works on top of other apps)
* 🌙 Dark mode support

---

## 📸 Screenshots

<p>
  <img src="https://res.cloudinary.com/dezwll9jv/image/upload/v1776359794/Screenshot_20260416-221023_hkvajn.jpg" width="250"/>
  <img src="https://res.cloudinary.com/dezwll9jv/image/upload/v1776360670/Screenshot_20260416-225657_brjy37.jpg" width="250"/>
</p>

---

## 🎥 Demo

[▶️ Watch Demo](https://res.cloudinary.com/dezwll9jv/video/upload/v1776359880/IMG_20260416_222200_133_mcw9ua.mp4)

---

## 📦 Download APK

You can download and try the app directly:

<p>
  <a href="https://drive.google.com/file/d/1adiZhjVbYn2wG9zYeG3npH0uK62LU80d/view?usp=sharing">
    <img src="https://img.shields.io/badge/Download-APK-blue?style=for-the-badge&logo=android"/>
  </a>
</p>

---

## 🧠 How It Works

MotionComfort reduces motion sickness by addressing **sensory conflict** between:

* 👁️ Visual input (what you see)
* 🦻 Vestibular system (what your body feels)

By synchronizing visual cues with real-world motion using the device's accelerometer, the app helps align both systems and reduce discomfort.

---

## 🛠 Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **MVVM Architecture**
* **Foreground Service**
* **SensorManager (Accelerometer)**
* **Custom Canvas Rendering**
* **Overlay (SYSTEM_ALERT_WINDOW)**

---

## ⚙️ Permissions Used

* `SYSTEM_ALERT_WINDOW` → Display overlay on top of other apps
* `FOREGROUND_SERVICE` → Run motion tracking continuously
* `FOREGROUND_SERVICE_SPECIAL_USE` → Required for modern Android versions
* `POST_NOTIFICATIONS` → Foreground service notification

---

## 🚀 Getting Started

1. Clone the repository

```bash
git clone https://github.com/MidhlajAm/MotionComfortJP.git
```

2. Open in Android Studio

3. Run on a real device (recommended)

4. Grant required permissions:

   * Overlay permission
   * Notification permission

---

## ⚠️ Note

* Best experienced on a **real device** (sensor-based app)
* Requires overlay permission for full functionality
* Works in real-time using device sensors

---

## 📌 Future Improvements

* Smoother motion physics
* AI-based motion prediction
* More visual cue styles
* UI animations & polish

---

## 👨‍💻 Author

**Midhlaj AM**

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!

