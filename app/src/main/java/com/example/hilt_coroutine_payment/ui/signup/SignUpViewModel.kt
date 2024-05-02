package com.example.hilt_coroutine_payment.ui.signup

import android.app.Application
import android.content.Context
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hilt_coroutine_payment.data.model.UserInfo
import com.example.hilt_coroutine_payment.util.RegexUtil
import com.example.hilt_coroutine_payment.util.SharedPrefUtil
import com.example.hilt_coroutine_payment.util.SignInType
import com.firebase.ui.auth.data.model.User

class SignUpViewModel(app: Application) : AndroidViewModel(app) {
    private val shared = SharedPrefUtil(app)

    private val userInfo = MutableLiveData<UserInfo>()

    private val name = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    private val phone = MutableLiveData<String>()
    private val role = MutableLiveData<String>()
    private val pwd = MutableLiveData<String>()
    private val pwdConfirm = MutableLiveData<String>()


    fun checkName(input: Editable?): Boolean {
        name.value = input?.trim().toString()
        return RegexUtil.checkName(input?.trim().toString())
    }

    fun checkEmail(input: Editable?): Boolean {
        email.value = input?.trim().toString()
        return RegexUtil.checkEmail(input?.trim().toString())
    }

    fun checkPhone(input: Editable?): Boolean {
        phone.value = input.toString()
        return RegexUtil.checkPhone(input.toString())
    }

    fun checkRole(item: String): Boolean {
        role.value = item
        return item.isNotEmpty()
    }

    fun checkPwd(input: Editable?): Boolean {
        pwd.value = input.toString()
        return RegexUtil.checkPassword(input.toString())
    }

    fun checkPwdConfirm(input: Editable?): Boolean {
        pwd.value?.let {
            pwdConfirm.value = input.toString()
            return RegexUtil.checkConfirmPassword(it, input.toString())
        }
        return false
    }

    fun saveFirstInfo() {
        userInfo.value = UserInfo(
            name = name.value,
            email = email.value,
            phone = phone.value
        )
//        shared.setStringPreferences(USER_NAME, name.value)
//        shared.setStringPreferences(EMAIL, email.value)
//        shared.setStringPreferences(PHONE, phone.value)
        shared.setRole(role.value)
    }

    fun saveSecondInfo() {
        userInfo.value = userInfo.value?.copy(
            point = 5000 // 5000 포인트 부여
        )
        userInfo.value?.let { shared.setUserInfo(it) }
        shared.setPassword(pwdConfirm.value)
        shared.setSignInType(SignInType.SELF)
    }

    fun getUserInfo(): String {
        val name = shared.getUserInfo().name
        val email = shared.getUserInfo().email
        val phone = shared.getUserInfo().phone
        val role = shared.getRole("")
        val pwd = shared.getPassword("")
        return "$name\n$email\n$phone\n$role\n$pwd"

    }
}