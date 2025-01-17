plugins {
    id("com.android.application")
}

android {
    namespace = "com.avintangu.bizsmat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.avintangu.bizsmat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation ("com.google.android.flexbox:flexbox:3.0.0")
    implementation ("com.google.api-client:google-api-client:2.6.0")
    implementation ("com.google.oauth-client:google-oauth-client-jetty:1.36.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.0-alpha04")
    implementation("org.jsoup:jsoup:1.11.1")
}