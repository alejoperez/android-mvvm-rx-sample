package com.mvvm.rx.sample.data.photos

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.rx.sample.data.room.Photo
import com.mvvm.rx.sample.livedata.Event

interface IPhotosDataSource {

    fun getPhotos(context: Context): LiveData<Event<List<Photo>>>

    fun savePhotos(context: Context, photos: List<Photo>)

}