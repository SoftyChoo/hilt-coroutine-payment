package com.example.hilt_coroutine_payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hilt_coroutine_payment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding){


    }
}