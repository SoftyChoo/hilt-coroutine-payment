package com.example.hilt_coroutine_payment

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, R.string.KAKAO_NATIVE_APP_KEY.toString())


        val OAUTH_CLIENT_ID = "FPLZI_y5M0hRJ1zqCaUD"
        val OAUTH_CLIENT_SECRET = "dfzmdbG3Ej"
        val OAUTH_CLIENT_NAME = BuildConfig.APPLICATION_ID

        // Naver SDK 초기화
        NaverIdLoginSDK.initialize(this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME)

    }
}