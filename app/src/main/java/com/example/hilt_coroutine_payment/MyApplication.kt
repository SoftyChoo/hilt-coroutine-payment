package com.example.hilt_coroutine_payment

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        val KAKAO_NATIVE_APP_KEY = "cfd52d78a38a0d71c022e4d129d4636e"
        KakaoSdk.init(this, R.string.KAKAO_NATIVE_APP_KEY.toString())

//        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)

//        val OAUTH_CLIENT_ID = "8cWEYtwRpoPkyV4eCLG5"
//        val OAUTH_CLIENT_SECRET = "ZN2UoEEa4J"
//        val OAUTH_CLIENT_NAME = BuildConfig.APPLICATION_ID
//
//
//
//        // Naver SDK 초기화
//        NaverIdLoginSDK.initialize(this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME)
//
//        // Kakao SDK 초기화
//        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }
}