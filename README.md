# Cultivitea-Mobile
This repository contains Adnroid APP implemented using Jetpack Compose. The app consumes API from this [API](https://github.com/Noitusanx/cultivitea.git)

## Introduction
Cultivitea is an app capable of detecting tea diseases and giving tea production information, such as forums and video, that can greatly assist farmers in maintaining the quality of their tea.

## APK Download Link
https://github.com/Cultivitea/Cultivitea-Mobile/releases/

## Tech Used
Cultivitea android app is developed using:
- Kotlin
- Android Studio
- Jetpack Compose

## Steps to Replicate
To replicate Cultivitea setup and run the app using Android Studio, follow these steps:

### Prerequisite
- Android Studio
- Java Development Kit

### Dependencies
| Package Name (with version if available)                                 | Purpose of the Package                          |
|--------------------------------------------------------------------------|-------------------------------------------------|
| com.squareup.retrofit2:retrofit:2.11.0                                   | HTTP client for Android and Java                |
| com.squareup.retrofit2:converter-gson:2.11.0                             | Converter for JSON to Java objects using Gson   |
| com.squareup.okhttp3:logging-interceptor:4.12.0                          | Logs HTTP request and response data             |
| androidx.datastore:datastore-preferences:1.0.0                           | DataStore implementation for storing preferences|
| androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1                    | Jetpack Compose integration for ViewModel       |
| androidx.compose.runtime:runtime-livedata                                | LiveData integration with Jetpack Compose       |
| androidx.compose.ui:ui-text-google-fonts:1.6.7                           | Support for Google Fonts in Jetpack Compose     |
| androidx.compose.material:material-icons-extended                        | Extended Material Icons for Jetpack Compose     |
| libs.androidx.material3.android                                          | Material Design Components for Android          |
| androidx.camera:camera-core:1.3.1                                        | Core library for CameraX                       |
| androidx.camera:camera-camera2:1.3.1                                     | Camera2 integration for CameraX                |
| androidx.camera:camera-view:1.3.1                                        | View class for CameraX                         |
| androidx.camera:camera-lifecycle:1.3.1                                   | Lifecycle management for CameraX               |
| io.coil-kt:coil-compose:2.6.0                                            | Image loading library for Jetpack Compose      |
| libs.androidx.core.ktx                                                   | Kotlin extensions for Android core libraries    |
| libs.androidx.lifecycle.runtime.ktx                                      | Kotlin extensions for Android lifecycle        |
| libs.androidx.activity.compose                                           | Compose integration for Android activities     |
| platform(libs.androidx.compose.bom)                                      | BOM for Jetpack Compose libraries               |
| libs.androidx.ui                                                         | Core UI components for Jetpack Compose          |
| libs.androidx.ui.graphics                                                | Graphics components for Jetpack Compose         |
| libs.androidx.ui.tooling.preview                                         | Tooling support for Jetpack Compose previews    |
| libs.androidx.navigation.compose                                         | Navigation support for Jetpack Compose          |
| libs.junit                                                               | JUnit for testing                               |
| libs.androidx.junit                                                      | AndroidX JUnit integration for testing          |
| libs.androidx.espresso.core                                              | Espresso core library for UI testing            |
| platform(libs.androidx.compose.bom)                                      | BOM for Jetpack Compose libraries (test)        |
| libs.androidx.ui.test.junit4                                             | JUnit4 support for Jetpack Compose testing      |
| libs.androidx.ui.tooling                                                 | Tooling support for Jetpack Compose (debug)     |
| libs.androidx.ui.test.manifest                                           | Manifest support for Jetpack Compose testing    |


#### 1. Clone this Project
`git clone https://github.com/Cultivitea/Cultivitea-Mobile.git`

#### 2. Open the Project in Android Studio
> 1. Open Android Studio.
> 2. Click on File > Open.
> 3. Navigate to the cloned project directory and select it.
> 4. Click OK to open the project.

#### 3. Set Up the Project
> 1. Ensure you have the required SDK and build tools installed. Android Studio should prompt you to install any missing dependencies.
> 2. Sync the project with Gradle files by clicking on the Sync Project with Gradle Files button.

#### 4. Build the Project
> Once the project is synced, build the project by clicking on Build > Make Project or using the Ctrl + F9 shortcut.

#### 5. Run the App on an Emulator or Device
- To run the app on an Android emulator:
> 1. Open the AVD Manager from the toolbar.
> 2. Create a new virtual device if you don't have one already.
> 3. Start the emulator.

- To run the app on a physical device:
> 1. Connect your Android device to your computer using a USB cable.
> 2. Ensure USB debugging is enabled on your device.
> 3. Click on the Run button or use the Shift + F10 shortcut to run the app.

#### 6. Grant Permissions
> If prompted, grant any necessary permissions for the app to function correctly, such as camera and storage permissions.

