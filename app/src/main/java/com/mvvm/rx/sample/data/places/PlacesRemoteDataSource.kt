package com.mvvm.rx.sample.data.places

import android.content.Context
import com.mvvm.rx.sample.data.room.Place
import com.mvvm.rx.sample.webservice.IApi
import com.mvvm.rx.sample.webservice.WebService
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class PlacesRemoteDataSource : IPlacesDataSource {

    override fun savePlaces(context: Context, places: List<Place>) = throw UnsupportedOperationException()

    override fun getPlaces(context: Context): Single<List<Place>> = WebService.createService(context, IApi::class.java).getPlaces()

}