package com.example.hilt_coroutine_payment.ui.paymentresult

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hilt_coroutine_payment.MainActivity
import com.example.hilt_coroutine_payment.R
import com.example.hilt_coroutine_payment.databinding.ActivityPaymentResultBinding
import com.example.hilt_coroutine_payment.util.Extension.toast

class PaymentResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPaymentResultBinding.inflate(layoutInflater) }

    private lateinit var resultDataList: List<String>
    private var isSuccess: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        isSuccess = intent?.getBooleanExtra(EXTRA_RESULT, false) == true
        resultDataList = intent?.getStringArrayListExtra(EXTRA_DATA).orEmpty()

        toast(if (isSuccess) "::결제 성공::" else "::결제 실패::")

        initView()
    }

    private fun initView() = with(binding){
        result.text = resultDataList.toString()

        btnNext.setOnClickListener {
            startActivity(Intent(this@PaymentResultActivity, MainActivity::class.java))
            finishAffinity()
        }
    }

    private fun bind() {

    }

    companion object {
        private const val EXTRA_RESULT = "extraResult"
        private const val EXTRA_DATA = "extraData"

        fun getIntent(context: Context, result: Boolean, data: ArrayList<String>): Intent {
            return Intent(context, PaymentResultActivity::class.java).apply {
                putExtra(EXTRA_RESULT, result)
                putStringArrayListExtra(EXTRA_DATA, data)
            }
        }
    }
}