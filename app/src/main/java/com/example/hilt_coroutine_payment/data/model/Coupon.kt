package com.example.hilt_coroutine_payment.data.model


/**
 * @param name 쿠폰 이름
 * @param discount 할인율/할인금액
 * @param type 정률 | 정액
 */
data class Coupon(
    val name: String,
    val discount: Int,
    val type: String
)
