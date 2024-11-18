plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.innotech.healapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.innotech.healapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
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

    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("androidx.datastore:datastore-preferences:1.1.1")

    // Firebase BOM (Bill of Materials) - Corrected quotes
    implementation(platform("com.google.firebase:firebase-bom:32.0.0")) // Correct usage of platform

    // Firebase dependencies (no version needed as BOM handles it)
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-messaging:24.0.3")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-firestore:25.1.1")

    // Material Calendar
    implementation ("com.prolificinteractive:material-calendarview:1.4.3")

    // Jetpack Compose and Material 3 dependencies
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    // Additional Compose UI dependencies
    implementation("androidx.compose.foundation:foundation:1.7.5")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.5")


    // Preferences for Android
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.11.0")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

