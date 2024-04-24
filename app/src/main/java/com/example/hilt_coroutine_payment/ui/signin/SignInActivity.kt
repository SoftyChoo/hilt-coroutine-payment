package com.example.hilt_coroutine_payment.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hilt_coroutine_payment.MainActivity
import com.example.hilt_coroutine_payment.util.Extension.toast
import com.example.hilt_coroutine_payment.R
import com.example.hilt_coroutine_payment.data.model.UserInfo
import com.example.hilt_coroutine_payment.databinding.ActivitySignInBinding
import com.example.hilt_coroutine_payment.ui.signup.SignUpActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    private val viewModel: SignInViewModel by viewModels()

    companion object{
        private const val TAG = "SignInActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        btnKakao.setOnClickListener { signInKakao()}

        btnNaver.setOnClickListener {

        }
        btnGoogle.setOnClickListener {

        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity,SignUpActivity::class.java)
        }

    }

    private fun initViewModel() = with(viewModel) {
        isSaved.observe(this@SignInActivity, Observer {
            if(it){
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

    }

    /**
     * Sign With Kakao
     */

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    private fun signInKakao() {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패 : ${error.message}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    toast("로그인에 성공했습니다")
                    Log.i(TAG, "카카오톡으로 로그인 성공 : ${token.accessToken}")
                    getKakaoUserInfo()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    fun getKakaoUserInfo(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {

                val userInfo = UserInfo(
                    name = user.kakaoAccount?.profile?.nickname,
                    email = user.kakaoAccount?.email,
                    phone = user.kakaoAccount?.phoneNumber
                )
                viewModel.saveUserInfo(userInfo)
            }
        }
    }
}