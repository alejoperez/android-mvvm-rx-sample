package com.mvvm.rx.sample.data.user

import android.content.Context
import com.mvvm.rx.sample.data.room.User
import com.mvvm.rx.sample.data.preference.PreferenceManager
import com.mvvm.rx.sample.webservice.LoginRequest
import com.mvvm.rx.sample.webservice.LoginResponse
import com.mvvm.rx.sample.webservice.RegisterRequest
import com.mvvm.rx.sample.webservice.RegisterResponse
import io.reactivex.Completable
import io.reactivex.Single

class UserRepository private constructor(
        private val localDataSource: IUserDataSource = UserLocalDataSource(),
        private val remoteDataSource: IUserDataSource = UserRemoteDataSource()) : IUserDataSource {

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(): UserRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: UserRepository().also { INSTANCE = it }
        }
    }

    override fun saveUser(context: Context, user: User) = localDataSource.saveUser(context, user)

    override fun getUser(context: Context): Single<User> = localDataSource.getUser(context)

    override fun login(context: Context, request: LoginRequest): Single<LoginResponse> =
            remoteDataSource.login(context, request)
                    .doOnSuccess {
                        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, it.accessToken)
                        localDataSource.saveUser(context, it.toUser())
                    }

    override fun register(context: Context, request: RegisterRequest): Single<RegisterResponse> =
            remoteDataSource.register(context, request)
                    .doOnSuccess {
                        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, it.accessToken)
                        localDataSource.saveUser(context, it.toUser())
                    }


    override fun isLoggedIn(context: Context): Single<Boolean> = localDataSource.isLoggedIn(context)

    override fun logout(context: Context): Completable = localDataSource.logout(context)

}
