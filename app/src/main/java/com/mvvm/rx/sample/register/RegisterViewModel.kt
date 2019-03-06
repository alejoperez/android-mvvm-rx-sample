package com.mvvm.rx.sample.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.mvvm.rx.sample.R
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.user.UserRepository
import com.mvvm.rx.sample.databinding.BindingAdapters
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.utils.*
import com.mvvm.rx.sample.webservice.RegisterRequest
import com.mvvm.rx.sample.webservice.RegisterResponse

class RegisterViewModel(application: Application): BaseViewModel(application) {

    val name = ObservableField("")
    val email = ObservableField("")
    val password = ObservableField("")

    val errorName = ObservableInt(BindingAdapters.EMPTY)
    val errorEmail = ObservableInt(BindingAdapters.EMPTY)
    val errorPassword = ObservableInt(BindingAdapters.EMPTY)

    val isLoading = ObservableField(false)

    val registerEvent = MutableLiveData<Event<RegisterResponse>>()

    fun register() {
        if (isValidForm()) {
            UserRepository.getInstance().register(getApplication(), RegisterRequest(name.getValueOrDefault(), email.getValueOrDefault(), password.getValueOrDefault()))
                    .applyIoAndMainThreads()
                    .doOnSubscribe { showProgress() }
                    .doAfterTerminate { hideProgress() }
                    .subscribe(
                            {
                                registerEvent.value = Event.success(it)
                            },
                            {
                                registerEvent.value = it.getEventError()
                            }
                    )
                    .addTo(compositeDisposable)
        } else {
            errorName.checkField(R.string.error_name_empty,isValidName())
            errorEmail.checkField(R.string.error_invalid_email,isValidEmail())
            errorPassword.checkField(R.string.error_empty_password,isValidPassword())
        }
    }

    private fun isValidName(): Boolean = name.getValueOrDefault().isNotEmpty()

    private fun isValidEmail(): Boolean = email.getValueOrDefault().isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.getValueOrDefault()).matches()

    private fun isValidPassword(): Boolean = password.getValueOrDefault().isNotEmpty()

    private fun isValidForm(): Boolean = isValidName() && isValidEmail() && isValidPassword()

    private fun showProgress() = isLoading.set(true)

    private fun hideProgress() = isLoading.set(false)
}