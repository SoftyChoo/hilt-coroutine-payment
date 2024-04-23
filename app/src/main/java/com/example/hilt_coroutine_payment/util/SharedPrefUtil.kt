package com.example.hilt_coroutine_payment.util

import android.content.Context

class SharedPrefUtil(context: Context) {

    private val pref = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)

    /**
     * String 설정
     */
    fun setStringPref(key: String, value: String?) {
        pref.edit().putString(key, value).apply()
    }

    /**
     * String 반환
     */
    fun getStringPref(key: String, defaultValue: String?): String? {
        return pref.getString(key, defaultValue)
    }
    /**
     * Int 설정
     */
    fun setIntPref(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    /**
     * Int 반환
     */
    fun getIntPref(key: String, defaultValue: Int): Int {
        return pref.getInt(key, defaultValue)
    }

    /**
     * Boolean 설정
     */
    fun setBooleanPref(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    /**
     * Boolean 반환
     */
    fun getBooleanPref(key: String, defaultValue: Boolean): Boolean {
        return pref.getBoolean(key, defaultValue)
    }

    /**
     * Preferences key, value 삭제
     */
    fun removeKeyPref(key: String) {
        pref.edit().remove(key).apply()
    }

    /**
     * Preference 초기화
     * */
    fun clearPref() {
        pref.edit().clear().apply()
    }
}