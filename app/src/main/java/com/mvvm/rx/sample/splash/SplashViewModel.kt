package com.mvvm.rx.sample.splash

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.user.UserRepository
import com.mvvm.rx.sample.livedata.Event

class SplashViewModel(application: Application): BaseViewModel(application) {

    val isUserLoggedEvent = MutableLiveData<Event<Boolean>>()

    fun isUserLoggedIn() {
        isUserLoggedEvent.value = Event.success(UserRepository.getInstance().isLoggedIn(getApplication()))
    }

}