package com.mvvm.rx.sample.data.user

import android.content.Context
import com.mvvm.rx.sample.data.room.User
import com.mvvm.rx.sample.webservice.*
import io.reactivex.Single

class UserRemoteDataSource : IUserDataSource {

    override fun login(context: Context, request: LoginRequest): Single<LoginResponse> = WebService.createService(context, IApi::class.java).login(request)

    override fun register(context: Context, request: RegisterRequest): Single<RegisterResponse> = WebService.createService(context, IApi::class.java).register(request)

    override fun logout(context: Context) = throw UnsupportedOperationException()

    override fun getUser(context: Context): Single<User> = throw UnsupportedOperationException()

    override fun saveUser(context: Context, user: User) = throw UnsupportedOperationException()

    override fun isLoggedIn(context: Context): Single<Boolean> = throw UnsupportedOperationException()
}