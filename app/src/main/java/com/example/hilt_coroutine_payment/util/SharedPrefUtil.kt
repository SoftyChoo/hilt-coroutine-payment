package com.example.hilt_coroutine_payment.util

import android.content.Context
import com.example.hilt_coroutine_payment.data.model.UserInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefUtil(context: Context) {

    private val pref = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)

    companion object {
        private val USER_INFORMATION_KEY = "user_information_key"
        private val USER_BOOLEAN_KEY = "user_boolean_key"
        private val SIGN_IN_TYPE_KEY = "sign_in_type_key"
    }

    /**
     * User Data 저장
     */
    fun setUserInfo(value: UserInfo) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor = pref?.edit()
        editor?.putString(USER_INFORMATION_KEY, json)
        editor?.apply()
    }

    /**
     * User Data 반환
     */
    fun getUserInfo(): UserInfo {
        val json = pref?.getString(USER_INFORMATION_KEY, null)

        return if (json != null) {
            val gson = Gson()
            val storedData: UserInfo = gson.fromJson(json, object : TypeToken<UserInfo>() {}.type)
            storedData
        } else {
            UserInfo()
        }
    }

    /**
     * User Data 삭제
     */
    fun deleteUserInfo() {
        pref.edit().remove(USER_INFORMATION_KEY).apply()
    }

    fun setUserBoolean(boolean: Boolean) {
        pref.edit().putBoolean(USER_BOOLEAN_KEY, boolean).apply()
    }

    fun getUserBoolean(defaultValue: Boolean): Boolean {
        return pref.getBoolean(USER_BOOLEAN_KEY,defaultValue)
    }

    fun deleteUserBoolean() {
        pref.edit().remove(USER_BOOLEAN_KEY).apply()
    }

    /////////==========================///////////

    /**
     * Preference 초기화
     * */
    fun clearPref() {
        pref.edit().clear().apply()
    }

    fun setSignInType(type:SignInType){
        pref.edit().putString(SIGN_IN_TYPE_KEY,type.toString()).apply()
    }

    fun getSignInType(): SignInType {
        val type = pref.getString(SIGN_IN_TYPE_KEY, null)
        return when (type) {
            SignInType.kAKAO.toString() -> SignInType.kAKAO
            SignInType.NAVER.toString() -> SignInType.NAVER
            SignInType.GOOGLE.toString() -> SignInType.GOOGLE
            else -> SignInType.NOTHING
        }
    }
}