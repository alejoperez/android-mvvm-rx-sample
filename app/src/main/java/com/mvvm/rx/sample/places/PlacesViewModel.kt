package com.mvvm.rx.sample.places

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.room.Place
import com.mvvm.rx.sample.data.places.PlacesRepository
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.utils.addTo
import com.mvvm.rx.sample.utils.applyIoAndMainThreads
import com.mvvm.rx.sample.utils.getEventError

class PlacesViewModel(application: Application): BaseViewModel(application) {

    val places = MutableLiveData<Event<List<Place>>>()

    fun getPlaces() {
        PlacesRepository.getInstance().getPlaces(getApplication())
                .applyIoAndMainThreads()
                .subscribe(
                        {
                            places.value = Event.success(it)
                        },
                        {
                            places.value = it.getEventError()
                        }
                )
                .addTo(compositeDisposable)
    }

}