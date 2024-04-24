package com.example.hilt_coroutine_payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hilt_coroutine_payment.util.SharedPrefUtil
import com.example.hilt_coroutine_payment.util.SignInType

class MainViewModel(app : Application) : AndroidViewModel(app){
    private val shared = SharedPrefUtil(app)

    private val _signInType = MutableLiveData(SignInType.NOTHING)
    val signInType : LiveData<SignInType> get() = _signInType

    private val _isSignOut = MutableLiveData(false)
    val isSignOut : LiveData<Boolean> get() = _isSignOut

    fun checkSignInType(){
        _signInType.value = shared.getSignInType()
    }

    fun signOut(){
        shared.getSignInType()

        shared.clearPref()
        _isSignOut.value = true
    }

}