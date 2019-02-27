package com.mvvm.rx.sample.data.user

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.rx.sample.data.room.User
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.webservice.LoginRequest
import com.mvvm.rx.sample.webservice.LoginResponse
import com.mvvm.rx.sample.webservice.RegisterRequest
import com.mvvm.rx.sample.webservice.RegisterResponse

interface IUserDataSource {
    fun getUser(context: Context): LiveData<Event<User>>
    fun saveUser(context: Context,user: User)
    fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>>
    fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>>
    fun isLoggedIn(context: Context): Boolean
    fun logout(context: Context)
}