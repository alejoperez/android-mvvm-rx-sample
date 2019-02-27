package com.mvvm.rx.sample.main

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.room.User
import com.mvvm.rx.sample.data.user.UserRepository
import com.mvvm.rx.sample.livedata.Event

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val getUserEvent = MutableLiveData<Event<Unit>>()

    val user: LiveData<Event<User>> = Transformations.switchMap(getUserEvent) {
        UserRepository.getInstance().getUser(getApplication())
    }

    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        getUserEvent.value = Event.loading()
    }

    fun logout() {
        onLogoutSuccess.value = Event.success(UserRepository.getInstance().logout(getApplication()))
    }

}