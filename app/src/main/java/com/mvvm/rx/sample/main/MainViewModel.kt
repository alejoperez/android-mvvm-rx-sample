package com.mvvm.rx.sample.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.room.User
import com.mvvm.rx.sample.data.user.UserRepository
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.utils.addTo
import com.mvvm.rx.sample.utils.applyIoAndMainThreads

class MainViewModel(application: Application) : BaseViewModel(application) {

    val user = MutableLiveData<Event<User>>()

    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {

        UserRepository.getInstance().getUser(getApplication())
                .applyIoAndMainThreads()
                .subscribe(
                        {
                            user.value = Event.success(it)
                        },
                        {
                            user.value = Event.failure()
                        })
                .addTo(compositeDisposable)

        user.value = Event.loading()
    }

    fun logout() {
        UserRepository.getInstance().logout(getApplication())
                .applyIoAndMainThreads()
                .subscribe(
                        {
                            onLogoutSuccess.value = Event.success(Unit)
                        },
                        {
                            onLogoutSuccess.value = Event.failure()
                        })
                .addTo(compositeDisposable)
    }

}