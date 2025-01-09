import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    id("androidx.navigation.safeargs")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)

}

android {
    namespace = "com.sdmdevelopers.asturspot"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sdmdevelopers.asturspot"
        minSdk = 25
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Librería coil (imágenes)
    implementation(libs.coil)
    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    // BBDD
    implementation(libs.firebase.firestore)
    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //Log http
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //maps
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)

    //tests
    // Required -- JUnit 4 framework
    testImplementation("junit:junit:4.13.2")
// Optional -- Robolectric environment
    testImplementation("androidx.test:core:1.6.1")
// Optional -- Mockito ramework
    testImplementation("org.mockito:mockito-core:4.5.1")
// Optional -- mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
// Optional -- Mockk framework
    testImplementation("io.mockk:mockk:1.13.13")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.robolectric:robolectric:4.7.3")
    testImplementation("org.mockito:mockito-inline:4.0.0")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}