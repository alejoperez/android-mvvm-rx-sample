package com.mvvm.rx.sample.data.photos

import android.content.Context
import com.mvvm.rx.sample.data.room.Photo
import io.reactivex.Single

interface IPhotosDataSource {

    fun getPhotos(context: Context): Single<List<Photo>>

    fun savePhotos(context: Context, photos: List<Photo>)

}