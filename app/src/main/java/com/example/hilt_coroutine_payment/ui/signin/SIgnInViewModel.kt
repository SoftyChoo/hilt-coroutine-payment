package com.example.hilt_coroutine_payment.ui.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hilt_coroutine_payment.data.model.UserInfo
import com.example.hilt_coroutine_payment.util.SharedPrefUtil
import com.example.hilt_coroutine_payment.util.SignInType

class SignInViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved : LiveData<Boolean> get() = _isSaved

    fun saveUserInfo(userInfo : UserInfo){
        val sharedPref = SharedPrefUtil(app)
        sharedPref.apply {
            setUserInfo(userInfo)
            setUserBoolean(true)

            _isSaved.value = true
        }
    }
//    fun attemptSignIn(signInType: SignInType){
//        when(signInType){
//            SignInType.kAKAO -> signInKakao()
//            SignInType.NAVER -> signInNaver()
//            SignInType.GOOGLE -> signInGoogle()
//        }
//    }
//
//    private fun signInKakao(){
//
//    }
//
//    private fun signInNaver(){
//
//    }
//
//    private fun signInGoogle(){
//
//    }

}