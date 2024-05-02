package com.example.hilt_coroutine_payment.ui.signup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.hilt_coroutine_payment.MainActivity
import com.example.hilt_coroutine_payment.R
import com.example.hilt_coroutine_payment.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel? by activityViewModels()

    private var isCheckPwd: Boolean = false
    private var isCheckPwdConfirm: Boolean = false
    private var isCheckPolicy: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        binding.btnPrev.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnReg.setOnClickListener {
            pwd()
            pwdConfirm()
            policy()

            if (isCheckPwd && isCheckPwdConfirm && isCheckPolicy) {
                viewModel!!.saveSecondInfo()
                showDialog()
            }
        }
    }

    private fun pwd() = with(binding.layoutPwd) {

        isCheckPwd = viewModel!!.checkPwd(editText?.text)
        if (!isCheckPwd) {
            error = getString(R.string.error_pwd)
        }
        isErrorEnabled = !isCheckPwd

        if (isErrorEnabled) {
            editText?.doOnTextChanged { text, start, before, count ->
                error = null
            }
        }

    }

    private fun pwdConfirm() = with(binding.layoutConfirm) {

        isCheckPwdConfirm = viewModel!!.checkPwdConfirm(editText?.text)
        if (!isCheckPwdConfirm) {
            error = getString(R.string.error_pwd_confirm)
        }
        isErrorEnabled = !isCheckPwdConfirm

        if (isErrorEnabled) {
            editText?.doOnTextChanged { text, start, before, count ->
                error = null
            }
        }

    }

    private fun policy() {
        if (!binding.cbPolicy.isChecked) {
            binding.errorPolicy.text = getString(R.string.error_policy)
        } else {
            binding.errorPolicy.text = ""
        }
        isCheckPolicy = binding.cbPolicy.isChecked
    }


    private fun showDialog() {
        val userInfo = viewModel!!.getUserInfo()

        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setCancelable(false)
            setTitle(getString(R.string.sign_up_dialig_title))
            setMessage(userInfo)
            setPositiveButton(getString(R.string.confirm)) { dialog, _ ->
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                dialog.dismiss()
            }
            val dialog = create()
            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}