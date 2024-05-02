package com.example.hilt_coroutine_payment.util

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


object RegexUtil {
    private const val PASSWORD_MIN_COUNT = 6
    private const val PASSWORD_MAX_COUNT = 20
    private const val PHONE_MIN_COUNT = 11
    private const val NAME_MIN_COUNT = 2
    private const val NAME_MAM_COUNT = 20


    fun checkName(name: String): Boolean {
        val pattern = "^[ㄱ-ㅎ가-힣a-zA-Z]{$NAME_MIN_COUNT,$NAME_MAM_COUNT}$"
        val m = Pattern.compile(pattern).matcher(name)

        return !(!m.find() || name.isEmpty())
    }


    fun checkEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        val m = pattern.matcher(email)

        return !(!m.find() || email.isEmpty())
    }


    fun checkPhone(phone: String): Boolean {
        val pattern = "^[0-9]{$PHONE_MIN_COUNT}$"
        val m = Pattern.compile(pattern).matcher(phone)

        return !(!m.find() || phone.isEmpty())
    }


    fun checkPassword(password: String): Boolean {
        val pattern =
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_~])[a-zA-Z0-9!@#$%^&*?_~]{$PASSWORD_MIN_COUNT,$PASSWORD_MAX_COUNT}$"
        val m = Pattern.compile(pattern).matcher(password)

        return !(!m.find() || password.isEmpty())
    }


    fun checkConfirmPassword(password: String, confirm: String): Boolean {
        return password == confirm
    }


    fun checkValidPoint(userPoint: Int, use: Int): Boolean {
        return userPoint >= use
    }
}