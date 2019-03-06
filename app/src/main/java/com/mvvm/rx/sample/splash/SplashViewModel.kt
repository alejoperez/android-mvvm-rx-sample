package com.mvvm.rx.sample.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.user.UserRepository
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.utils.addTo
import com.mvvm.rx.sample.utils.applyIoAndMainThreads
import com.mvvm.rx.sample.utils.getEventError

class SplashViewModel(application: Application): BaseViewModel(application) {

    val isUserLoggedEvent = MutableLiveData<Event<Boolean>>()

    fun isUserLoggedIn() {
        UserRepository.getInstance().isLoggedIn(getApplication())
                .applyIoAndMainThreads()
                .subscribe(
                        {
                            isUserLoggedEvent.value = Event.success(it)
                        },
                        {
                            isUserLoggedEvent.value = it.getEventError()
                        }
                )
                .addTo(compositeDisposable)
    }

}