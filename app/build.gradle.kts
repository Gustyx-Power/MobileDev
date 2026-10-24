plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.gdgoc.luminawalls"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.gdgoc.luminawalls"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }

        // TODO: [Session 5] Aktifkan optimasi R8 pada mode Release saat belajar rilis produksi
        release {
            isMinifyEnabled = false // TODO: Ubah ke `true` untuk mengaktifkan R8 code shrinking & obfuscation
            isShrinkResources = false // TODO: Ubah ke `true` untuk menghapus aset yang tidak dipakai

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)

    // Core Android & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose UI & Material 3
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    debugImplementation(libs.androidx.compose.ui.tooling)

    // TODO: [Session 1] Tambahkan dependensi SplashScreen resmi Android di bawah sini:



    // TODO: [Session 3] Tambahkan DataStore Preferences untuk simpan setelan tema di bawah sini:



    // TODO: [Session 5] Tambahkan Networking (OkHttp) & Image Loading (Coil) di bawah sini:



}
