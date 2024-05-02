package com.example.hilt_coroutine_payment.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hilt_coroutine_payment.R
import com.example.hilt_coroutine_payment.databinding.FragmentFirstBinding
import com.example.hilt_coroutine_payment.databinding.FragmentSecondBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by activityViewModels()

    private var isCheckName: Boolean = false
    private var isCheckEmail: Boolean = false
    private var isCheckPhone: Boolean = false
    private var isCheckRole: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * 스피너
         */
        with(binding.role) {
            ArrayAdapter.createFromResource(
                requireContext(), R.array.role_array, android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                setAdapter(adapter)
            }
        }


        /**
         * 버튼 클릭 리스너
         */
        binding.btnNext.setOnClickListener {
            name()
            email()
            phone()
            role()

            if (isCheckName && isCheckEmail && isCheckPhone && isCheckRole) {
                viewModel.saveFirstInfo()
                findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
            }
        }
    }

    private fun name() {
        with(binding.layoutName) {
            isCheckName = viewModel.checkName(editText?.text)
            if (!isCheckName) {
                error = getString(R.string.error_name)
            }
            isErrorEnabled = !isCheckName

            if (isErrorEnabled) {
                editText?.doOnTextChanged { text, start, before, count ->
                    error = null
                }
            }
        }
    }

    private fun email() {
        with(binding.layoutEmail) {
            isCheckEmail = viewModel.checkEmail(editText?.text)
            if (!isCheckEmail) {
                error = getString(R.string.error_email)
            }
            isErrorEnabled = !isCheckEmail

            if (isErrorEnabled) {
                editText?.doOnTextChanged { text, start, before, count ->
                    error = null
                }
            }
        }
    }

    private fun phone() {
        with(binding.layoutPhone) {
            isCheckPhone = viewModel.checkPhone(editText?.text)
            if (!isCheckPhone) {
                error = getString(R.string.error_phone)
            }
            isErrorEnabled = !isCheckPhone

            if (isErrorEnabled) {
                editText?.doOnTextChanged { text, start, before, count ->
                    error = null
                }
            }
        }
    }

    private fun role() {
        with(binding.layoutRole) {
            isCheckRole = viewModel.checkRole(editText?.text.toString())
            if (!isCheckRole) {
                error = getString(R.string.choose_role)
            }
            isErrorEnabled = !isCheckRole

            if (isErrorEnabled) {
                editText?.doOnTextChanged { text, start, before, count ->
                    error = null
                }
            }
        }
    }

    companion object {
        const val TAG = "FirstFragment"
    }
}