plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appsuperhamburguesas01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appsuperhamburguesas01"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation(libs.recyclerview)
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("com.google.android.gms:play-services-maps:17.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}