plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.hilt_coroutine_payment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hilt_coroutine_payment"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    // When using the BoM, you don't specify versions in Firebase library dependencies
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

//    // Navigation
//    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
//    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    // 카카오 로그인 API 모듈
    implementation ("com.kakao.sdk:v2-all:2.20.1") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation ("com.kakao.sdk:v2-user:2.20.1") // 카카오 로그인 API 모듈
    implementation ("com.kakao.sdk:v2-share:2.20.1") // 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-common:2.20.1")
    implementation("com.kakao.sdk:v2-auth:2.20.1")
//    implementation("com.kakao.sdk:v2-user:2.20.1")

//
//    // 네이버 로그인 API 모듈
////    implementation("com.navercorp.nid:oauth:5.9.0") // jdk 11
//    implementation("com.navercorp.nid:oauth-jdk8:5.9.0") // jdk 8
//
//
//    // 구글 로그인 API 모듈
//    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
//    implementation("com.firebaseui:firebase-ui-auth:7.2.0")
//    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation("com.google.android.gms:play-services-auth:20.7.0")
//
//    // 토스 결제 API 모듈
//    implementation("com.github.tosspayments:payment-sdk-android:0.1.14")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}