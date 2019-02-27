package com.mvvm.rx.sample.photos

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.databinding.ObservableBoolean
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.room.Photo
import com.mvvm.rx.sample.data.photos.PhotosRepository
import com.mvvm.rx.sample.livedata.Event

class PhotosViewModel(application: Application): BaseViewModel(application) {

    val isLoading = ObservableBoolean(false)

    private val getPhotos = MutableLiveData<Event<Unit>>()

    val photos: LiveData<Event<List<Photo>>> = Transformations.switchMap(getPhotos) {
        PhotosRepository.getInstance().getPhotos(getApplication())
    }

    fun getPhotos() {
        showProgress()
        getPhotos.value = Event.loading()
    }

    private fun showProgress() = isLoading.set(true)

    fun hideProgress() = isLoading.set(false)

}