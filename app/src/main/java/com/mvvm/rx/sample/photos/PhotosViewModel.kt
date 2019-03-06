package com.mvvm.rx.sample.photos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.databinding.ObservableBoolean
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.room.Photo
import com.mvvm.rx.sample.data.photos.PhotosRepository
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.utils.addTo
import com.mvvm.rx.sample.utils.applyIoAndMainThreads
import com.mvvm.rx.sample.utils.getEventError

class PhotosViewModel(application: Application): BaseViewModel(application) {

    val isLoading = ObservableBoolean(false)

    val photos = MutableLiveData<Event<List<Photo>>>()

    fun getPhotos() {
        PhotosRepository.getInstance().getPhotos(getApplication())
                .applyIoAndMainThreads()
                .doOnSubscribe { showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribe(
                        {
                            photos.value = Event.success(it)
                        },
                        {
                            photos.value = it.getEventError()
                        })
                .addTo(compositeDisposable)
    }

    private fun showProgress() = isLoading.set(true)

    private fun hideProgress() = isLoading.set(false)

}