package com.example.hilt_coroutine_payment

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hilt_coroutine_payment.databinding.ActivityMainBinding
import com.example.hilt_coroutine_payment.ui.payment.PaymentActivity
import com.example.hilt_coroutine_payment.ui.signin.SignInActivity
import com.example.hilt_coroutine_payment.util.Extension.toast
import com.example.hilt_coroutine_payment.util.SignInType
import com.firebase.ui.auth.AuthUI
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels()

    companion object {
        private const val TAG = "MainActivity"

        private const val TIME_INTERVAL = 2000
        private var backPressedAt: Long = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewModel()
        backPressed()
    }

    private fun initViewModel() = with(viewModel) {

        signInType.observe(this@MainActivity, Observer { type ->
            when (type) {
                SignInType.kAKAO -> kakaoSignOut()
                SignInType.NAVER -> naverSignOut()
                SignInType.GOOGLE -> googleSignOut()
                SignInType.SELF -> selfSignOut()
                SignInType.NOTHING -> Unit
            }
        })

        isSignOut.observe(this@MainActivity, Observer {
            if (it) {
                toast("사용자 정보가 모두 삭제 되었습니다")
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        })

    }

    private fun selfSignOut() {
        signOut()
    }

    private fun initView() = with(binding) {
        btnPayment.setOnClickListener {
            val intent = Intent(this@MainActivity, PaymentActivity::class.java)
            startActivity(intent)
        }
        btnSignOut.setOnClickListener {
            viewModel.checkSignInType()
        }
    }

    private fun kakaoSignOut() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
        signOut()
    }

    private fun naverSignOut() {
        toast("네이버 로그아웃")

        // 어플리케이션 연동해제 (토큰 삭제)
        NidOAuthLogin().callDeleteTokenApi(object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
                NaverIdLoginSDK.logout()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d(TAG, "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d(TAG, "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
                NaverIdLoginSDK.logout()
            }

            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
                NaverIdLoginSDK.logout()
            }
        })

        signOut()
    }

    private fun googleSignOut() {
        toast("구글 로그아웃")
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        signOut()
    }

    private fun signOut() {
        viewModel.signOut()
    }


    /**
     * 뒤로가기 * 2 = 종료
     */
    private fun backPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                backPressedInvoke()
            }
        } else {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressedInvoke()
                }
            })
        }
    }

    fun backPressedInvoke() {
        if (backPressedAt + TIME_INTERVAL > System.currentTimeMillis()) {
            finish()
        } else {
            backPressedAt = System.currentTimeMillis()
            toast(getString(R.string.message_back_pressed))
        }
    }
}