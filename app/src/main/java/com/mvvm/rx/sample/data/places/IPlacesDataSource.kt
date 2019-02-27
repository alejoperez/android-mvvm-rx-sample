package com.mvvm.rx.sample.data.places

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.rx.sample.data.room.Place
import com.mvvm.rx.sample.livedata.Event

interface IPlacesDataSource {

    fun getPlaces(context: Context): LiveData<Event<List<Place>>>

    fun savePlaces(context: Context, places: List<Place>)
}