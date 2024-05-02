package com.example.hilt_coroutine_payment.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.example.hilt_coroutine_payment.R
import com.example.hilt_coroutine_payment.databinding.ActivityPaymentBinding
import com.example.hilt_coroutine_payment.util.Extension.hideKeyboard
import com.example.hilt_coroutine_payment.util.Extension.toast
import com.tosspayments.paymentsdk.view.PaymentMethod

class PaymentActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPaymentBinding.inflate(layoutInflater) }
    private val viewModel: PaymentViewModel by viewModels()

    private var amount: Int = 19500
    private var point: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding()
    }

    private fun binding() {

        viewModel.setPrice(amount)
        viewModel.setPriceString(String.format(getString(R.string.product_price), amount))

        /**
         * 배송 메모
         */
        with(binding.layoutShipping.memo) {
            ArrayAdapter.createFromResource(
                context, R.array.memo_array, android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                setAdapter(adapter)
            }
        }

        /**
         * 쿠폰
         */
        with(binding.layoutCoupon.layoutCoupon) {
            ArrayAdapter.createFromResource(
                applicationContext, R.array.coupon_array, android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.layoutCoupon.coupon.setAdapter(adapter)
            }

            binding.layoutCoupon.btnApplyCoupon.setOnClickListener {
                if (editText?.text?.isNotEmpty() == true) {
                    viewModel.setCoupon(editText?.text.toString())
                } else {
                    toast("선택된 쿠폰이 없습니다")
                }
            }
        }

        /**
         * 포인트
         */
        with(binding.layoutCoupon.layoutPoint) {
            viewModel.userInfo.observe(this@PaymentActivity) {
                if (it?.point != null && it?.point < 5000 || amount < 10000) {
                    editText?.text = SpannableStringBuilder("포인트를 사용할 수 없습니다.")
                    editText?.isEnabled = false
                    binding.layoutCoupon.btnApplyPoint.isEnabled = false
                }
            }

            binding.layoutCoupon.btnApplyPoint.setOnClickListener {
                hideKeyboard()

                if (editText?.text?.isNotEmpty() == true) {
                    val isValid = viewModel.checkValidPoint(editText?.text.toString())

                    if (!isValid) {
                        error = "포인트를 사용할 수 없습니다."
                    } else {
                        binding.layoutCoupon
                        viewModel.setPoint(editText?.text.toString())
                        point = editText?.text.toString().toInt()
                    }
                    isErrorEnabled = !isValid
                    binding.initPayment.isEnabled = isValid

                    editText?.doOnTextChanged { text, start, before, count ->
                        if (isErrorEnabled) {
                            error = null
                        }
                    }
                } else {
                    toast("포인트를 입력하지 않았습니다")
                }
            }
        }



        viewModel.total.observe(this) { amount ->
            if (amount != 0) {

                binding.initPayment.setOnClickListener {
                    val intent = Intent(
                        RequestPaymentActivity.getIntent(
                            this@PaymentActivity,
                            amount = amount.toDouble(),
                            clientKey = TEST_KEY,
                            customerKey = TEST_CUSTOMER_KEY,
                            orderId = "orderId",
                            orderName = "orderName",
                            currency = PaymentMethod.Rendering.Currency.KRW,
                            countryCode = "KR",
                            variantKey = "variantKey",
                            point = point
                        )
                    )
                    startActivity(intent)
                }
            }
        }
    }

    companion object {
        private const val TEST_KEY = "test_ck_AQ92ymxN34g7QxoG2Lxj8ajRKXvd"
        private const val TEST_CUSTOMER_KEY = "test_sk_6BYq7GWPVv2nlqO1jY95VNE5vbo1"
    }
}