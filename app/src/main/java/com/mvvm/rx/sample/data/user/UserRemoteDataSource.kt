package com.mvvm.rx.sample.data.user

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.rx.sample.data.room.User
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.webservice.*

class UserRemoteDataSource : IUserDataSource {

    override fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>> = WebService.createService(context, IApi::class.java).login(request)

    override fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>> = WebService.createService(context, IApi::class.java).register(request)

    override fun logout(context: Context) = throw UnsupportedOperationException()

    override fun getUser(context: Context): LiveData<Event<User>> = throw UnsupportedOperationException()

    override fun saveUser(context: Context, user: User) = throw UnsupportedOperationException()

    override fun isLoggedIn(context: Context): Boolean = throw UnsupportedOperationException()
}