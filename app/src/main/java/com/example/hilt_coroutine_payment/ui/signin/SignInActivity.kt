package com.example.hilt_coroutine_payment.ui.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hilt_coroutine_payment.R
import com.example.hilt_coroutine_payment.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {


    }
}