package com.mvvm.rx.sample.data.user

import android.content.Context
import com.mvvm.rx.sample.data.room.SampleDataBase
import com.mvvm.rx.sample.data.room.User
import com.mvvm.rx.sample.data.preference.PreferenceManager
import com.mvvm.rx.sample.webservice.LoginRequest
import com.mvvm.rx.sample.webservice.LoginResponse
import com.mvvm.rx.sample.webservice.RegisterRequest
import com.mvvm.rx.sample.webservice.RegisterResponse
import io.reactivex.Completable
import io.reactivex.Single

class UserLocalDataSource : IUserDataSource {

    override fun isLoggedIn(context: Context): Single<Boolean> = Single.just(PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty())

    override fun getUser(context: Context): Single<User> = SampleDataBase.getInstance(context).userDao().getUser()

    override fun saveUser(context: Context, user: User) = SampleDataBase.getInstance(context).userDao().saveUser(user)

    override fun logout(context: Context): Completable {
        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,"")
        return Completable.complete()
    }

    override fun login(context: Context, request: LoginRequest): Single<LoginResponse> = throw UnsupportedOperationException()

    override fun register(context: Context, request: RegisterRequest): Single<RegisterResponse> = throw UnsupportedOperationException()

}