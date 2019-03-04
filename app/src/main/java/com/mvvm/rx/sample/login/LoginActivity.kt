package com.mvvm.rx.sample.login

import android.arch.lifecycle.Observer
import com.android.databinding.library.baseAdapters.BR
import com.mvvm.rx.sample.R
import com.mvvm.rx.sample.base.BaseActivity
import com.mvvm.rx.sample.databinding.ActivityLoginBinding
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.livedata.Status
import com.mvvm.rx.sample.main.MainActivity
import com.mvvm.rx.sample.utils.EditTextUtils
import com.mvvm.rx.sample.webservice.LoginResponse
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity<LoginViewModel,ActivityLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_login
    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getVariablesToBind(): Map<Int, Any> = mapOf(
            BR.viewModel to viewModel,
            BR.etUtils to EditTextUtils
    )

    override fun initViewModel() {
        super.initViewModel()
        viewModel.loginEvent.observe(this, onLoginResponseObserver)
    }

    private val onLoginResponseObserver = Observer<Event<LoginResponse>> {
        if (it != null) {
            onLoginResponse(it)
        } else {
            onLoginFailure()
        }
    }

    private fun onLoginResponse(response: Event<LoginResponse>) {
        when (response.status) {
            Status.SUCCESS -> onLoginSuccess()
            Status.FAILURE -> onLoginFailure()
            Status.NETWORK_ERROR -> onNetworkError()
            else -> Unit
        }
    }

    private fun onLoginSuccess() {
        startActivity<MainActivity>()
        finishAffinity()
    }

    private fun onLoginFailure() = showAlert(R.string.error_invalid_credentials)
}
