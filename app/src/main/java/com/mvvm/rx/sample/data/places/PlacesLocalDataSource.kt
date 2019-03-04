package com.mvvm.rx.sample.data.places

import android.content.Context
import com.mvvm.rx.sample.data.room.Place
import com.mvvm.rx.sample.data.room.SampleDataBase
import io.reactivex.Single

class PlacesLocalDataSource: IPlacesDataSource {

    override fun savePlaces(context: Context, places: List<Place>) = SampleDataBase.getInstance(context).placeDao().savePlaces(places)

    override fun getPlaces(context: Context): Single<List<Place>> = SampleDataBase.getInstance(context).placeDao().getPlaces()

}