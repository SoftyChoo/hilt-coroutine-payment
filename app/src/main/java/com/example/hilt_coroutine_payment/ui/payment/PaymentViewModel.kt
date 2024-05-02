package com.example.hilt_coroutine_payment.ui.payment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hilt_coroutine_payment.data.model.Coupon
import com.example.hilt_coroutine_payment.data.model.UserInfo
import com.example.hilt_coroutine_payment.util.RegexUtil
import com.example.hilt_coroutine_payment.util.SharedPrefUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaymentViewModel(app: Application) : AndroidViewModel(app) {

    private val shared = SharedPrefUtil(app)

    private val price = MutableLiveData<Int>()
    val priceString = MutableLiveData<String>()
    val discount = MutableLiveData<Int>(0)
    val point = MutableLiveData<Int>(0)
    val total = MutableLiveData<Int>(0)
    val shipping = MutableLiveData<Int>(2500)

    val showProgress = MutableLiveData<Boolean>(true)

    private val _userInfo = MutableLiveData<UserInfo?>()
    val userInfo: LiveData<UserInfo?> = _userInfo

    private val couponList = arrayListOf<Coupon>(
        Coupon("30% 할인 쿠폰", 30, "정률"),
        Coupon("3000원 할인 쿠폰", 3000, "정액"),
    )

    init {
        viewModelScope.launch {
            _userInfo.postValue(
                UserInfo(
                    name = shared.getUserInfo().name ?: "",
                    email = shared.getUserInfo().email,
                    phone = shared.getUserInfo().phone,
                    address = shared.getUserInfo().address,
                    point = shared.getUserInfo().point
                )
            )
        }
    }

    fun setPrice(amount: Int) {
        price.postValue(amount)
        total.postValue(amount.plus(shipping.value ?: 0))
    }

    fun setPriceString(amount: String) {
        priceString.postValue(amount)
    }

    fun setCoupon(value: String) {
        couponList.filter {
            value == it.name
        }.map {
            if (it.type == "정률") {
                val discountValue = price.value?.times(it.discount)?.div(100) ?: 0
                discount.value = discountValue

                setTotal(coupon = discountValue, point = point.value ?: 0)
            } else if (it.type == "정액") {
                val discountValue = it.discount
                discount.value = discountValue

                setTotal(coupon = discountValue, point = point.value ?: 0)
            }
        }
    }

    fun setPoint(value: String) {
        var discountValue = 0
        if (value.isNotEmpty() || value != "0") {
            discountValue = value.toInt()
            point.value = discountValue

            setTotal(coupon = discount.value ?: 0, point = discountValue)
        }
    }

    fun checkValidPoint(use: String): Boolean {
        val userPoint = _userInfo.value?.point ?: 0

        return RegexUtil.checkValidPoint(userPoint, use.toInt())
    }

    fun savePoint(point: Int) {
        Log.d("Save Point", "$point 포인트 사용")
        val userPoint = _userInfo.value?.point ?: shared.getUserPoint(0)
        shared.setUserPoint(userPoint.minus(point))
    }

    private fun setTotal(coupon: Int, point: Int) {
        total.value = price.value?.minus(coupon)?.minus(point)?.plus(shipping.value ?: 0)
    }

    fun showProgress() {
        viewModelScope.launch {
            delay(2000)
            showProgress.postValue(false)
        }
    }
}