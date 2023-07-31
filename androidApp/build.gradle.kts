plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.devmikespb.simpleweather.android"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.devmikespb.simpleweather.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        val OPEN_WEATHER_API_KEY: String by project
        buildConfigField("String", "API_KEY", "\"$OPEN_WEATHER_API_KEY\"")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val composeVersion = "1.4.3"
    // Default dependencies.
    implementation(project(":shared"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.activity:activity-compose:1.7.1")
    // DI.
    implementation("io.insert-koin:koin-android:3.4.0")
    implementation("io.insert-koin:koin-androidx-compose:3.4.0")
    // Lifecycle.
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
}
