plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.vsdhoni5034.mlmate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vsdhoni5034.mlmate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    // CameraX core library using the camera2 implementation
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)

    //tensor flow ML
    implementation(libs.tensorflow.lite.task.vision)
    implementation(libs.tensorflow.lite.gpu.delegate.plugin)
    implementation(libs.tensorflow.lite.gpu)

    //Object detection using google media pipe
    implementation(libs.tasks.vision)

    //accompanist library
    implementation(libs.accompanist.permissions)

    //compose navigation
    implementation(libs.androidx.navigation.compose)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    //material icons
    implementation(libs.androidx.material.icons.extended)

    //dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //hilt with compose navigation
    implementation(libs.androidx.hilt.navigation.compose)

    //Preferences data store
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}