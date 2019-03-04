package com.mvvm.rx.sample.data.places

import android.content.Context
import com.mvvm.rx.sample.data.room.Place
import io.reactivex.Single

class PlacesRepository private constructor(
        private val localDataSource: IPlacesDataSource = PlacesLocalDataSource(),
        private val remoteDataSource: IPlacesDataSource = PlacesRemoteDataSource()) : IPlacesDataSource {


    private var hasCache = false

    companion object {
        @Volatile
        private var INSTANCE: PlacesRepository? = null

        fun getInstance(): PlacesRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PlacesRepository().also { INSTANCE = it }
        }
    }

    override fun getPlaces(context: Context): Single<List<Place>> {
        return if (hasCache) {
            localDataSource.getPlaces(context)
        } else {
            remoteDataSource.getPlaces(context)
                    .doAfterSuccess {
                        savePlaces(context,it)
                        hasCache = true
                    }
        }
    }

    override fun savePlaces(context: Context,places: List<Place>) = localDataSource.savePlaces(context, places)

    fun invalidateCache() {
        hasCache = false
    }
}