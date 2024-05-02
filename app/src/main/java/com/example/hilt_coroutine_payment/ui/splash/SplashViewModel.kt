package com.example.hilt_coroutine_payment.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hilt_coroutine_payment.util.SharedPrefUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(app: Application) : AndroidViewModel(app) {

    private val shared = SharedPrefUtil(app)

    private val _showSplash = MutableLiveData<Boolean>(true)
    val showSplash : LiveData<Boolean> get() =  _showSplash

    init {
        viewModelScope.launch {
            delay(3000) //3초 보여주기
            _showSplash.postValue(false)
        }
    }

    fun isLogin(): Boolean {
        return shared.getUserBoolean(false)
    }
}