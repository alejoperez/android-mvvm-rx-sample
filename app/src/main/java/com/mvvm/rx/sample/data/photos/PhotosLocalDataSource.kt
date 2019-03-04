package com.mvvm.rx.sample.data.photos

import android.content.Context
import com.mvvm.rx.sample.data.room.Photo
import com.mvvm.rx.sample.data.room.SampleDataBase
import io.reactivex.Single

class PhotosLocalDataSource: IPhotosDataSource {

    override fun savePhotos(context: Context, photos: List<Photo>) = SampleDataBase.getInstance(context).photoDao().savePhotos(photos)

    override fun getPhotos(context: Context): Single<List<Photo>> = SampleDataBase.getInstance(context).photoDao().getPhotos()

}