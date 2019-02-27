package com.mvvm.rx.sample.data.photos

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.rx.sample.data.room.Photo
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.webservice.IApi
import com.mvvm.rx.sample.webservice.WebService
import java.lang.UnsupportedOperationException

class PhotosRemoteDataSource : IPhotosDataSource {

    override fun savePhotos(context: Context, photos: List<Photo>) = throw UnsupportedOperationException()

    override fun getPhotos(context: Context): LiveData<Event<List<Photo>>> = WebService.createService(context, IApi::class.java).getPhotos()

}