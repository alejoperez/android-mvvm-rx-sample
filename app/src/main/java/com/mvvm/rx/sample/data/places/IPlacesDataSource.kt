package com.mvvm.rx.sample.data.places

import android.content.Context
import com.mvvm.rx.sample.data.room.Place
import io.reactivex.Single

interface IPlacesDataSource {

    fun getPlaces(context: Context): Single<List<Place>>

    fun savePlaces(context: Context, places: List<Place>)
}