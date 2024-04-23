package com.example.hilt_coroutine_payment.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hilt_coroutine_payment.MainActivity
import com.example.hilt_coroutine_payment.R
import com.example.hilt_coroutine_payment.ui.signin.SignInActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.showSplash.observe(this) { showSplash ->
            if (!showSplash) {
                if (viewModel.isLogin()) {
                    startMainActivity()
                } else {
                    startLoginActivity()
                }
            }
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}