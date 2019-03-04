package com.mvvm.rx.sample.data.photos

import android.content.Context
import com.mvvm.rx.sample.data.room.Photo
import io.reactivex.Single

class PhotosRepository private constructor(
        private val localDataSource: IPhotosDataSource = PhotosLocalDataSource(),
        private val remoteDataSource: IPhotosDataSource = PhotosRemoteDataSource()) : IPhotosDataSource {


    private var hasCache = false

    companion object {
        @Volatile
        private var INSTANCE: PhotosRepository? = null

        fun getInstance(): PhotosRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PhotosRepository().also { INSTANCE = it }
        }
    }

    override fun getPhotos(context: Context): Single<List<Photo>> {
        return if (hasCache) {
            localDataSource.getPhotos(context)
        } else {
            remoteDataSource.getPhotos(context)
                    .doAfterSuccess {
                        savePhotos(context,it)
                        hasCache = true
                    }

        }
    }

    override fun savePhotos(context: Context,photos: List<Photo>) = localDataSource.savePhotos(context, photos)

    fun invalidateCache() {
        hasCache = false
    }
}