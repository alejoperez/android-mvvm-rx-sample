package com.mvvm.rx.sample.webservice

import android.arch.lifecycle.LiveData
import com.mvvm.rx.sample.data.room.Photo
import com.mvvm.rx.sample.data.room.Place
import com.mvvm.rx.sample.livedata.Event
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApi {

    @POST("user/login")
    fun login(@Body request: LoginRequest): LiveData<Event<LoginResponse>>

    @POST("user/register")
    fun register(@Body request: RegisterRequest): LiveData<Event<RegisterResponse>>

    @GET("places")
    fun getPlaces(): LiveData<Event<List<Place>>>

    @GET("photos")
    fun getPhotos(): LiveData<Event<List<Photo>>>

}