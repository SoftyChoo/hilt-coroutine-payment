package com.example.hilt_coroutine_payment.data.model

data class UserInfo (
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val isLogin: Boolean = false,
    val point: Int? = null
)