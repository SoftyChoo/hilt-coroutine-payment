package com.example.hilt_coroutine_payment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_native_key))

//        val OAUTH_CLIENT_ID = "8cWEYtwRpoPkyV4eCLG5"
//        val OAUTH_CLIENT_SECRET = "ZN2UoEEa4J"
//        val OAUTH_CLIENT_NAME = BuildConfig.APPLICATION_ID
//
//        val KAKAO_NATIVE_APP_KEY = "5f623e886b3a8129a377a1a63c63b015"
//
//        // Naver SDK 초기화
//        NaverIdLoginSDK.initialize(this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME)
//
//        // Kakao SDK 초기화
//        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }
}