# REDCORE Game Booster (starter)

Independent black/red Game Space-style Android starter for POCO M4 Pro 4G.

## Current starter features
- Black/red dashboard
- Battery percentage and Android version
- Quick launch buttons for Free Fire, YouTube, WhatsApp
- Shortcuts to Android display, notification/DND access, and app battery settings

## Important limitations
- This app does not root the phone or unlock CPU/GPU/FPS limits.
- Android/MIUI may restrict app discovery or opening apps depending on version and permissions.
- DND policy changes require the user to grant access in system settings.
- Actual FPS overlays, touch mapping, cooling controls, and hardware triggers require device/game support and may not be available to third-party apps.

## Build
Open the folder in Android Studio and sync Gradle. Use a compatible JDK (17) and Android SDK 35.

## Build APK from GitHub (phone-friendly)
1. Open the repository's **Actions** tab and select **Build REDCORE APK**.
2. Wait for the workflow to finish successfully.
3. Open that workflow run and download the **REDCORE-debug-APK** artifact.
4. Extract the downloaded artifact ZIP; install `app-debug.apk` on the POCO M4 Pro 4G and test it.

The workflow builds a debug APK automatically. This starter has not yet been verified on a physical phone, and it does not unlock hardware-level FPS or cooling controls.
