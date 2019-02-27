package com.mvvm.rx.sample.places

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mvvm.rx.sample.base.BaseViewModel
import com.mvvm.rx.sample.data.room.Place
import com.mvvm.rx.sample.data.places.PlacesRepository
import com.mvvm.rx.sample.livedata.Event

class PlacesViewModel(application: Application): BaseViewModel(application) {

    private val placesEvent = MutableLiveData<Event<Unit>>()
    val places: LiveData<Event<List<Place>>> = Transformations.switchMap(placesEvent) {
        PlacesRepository.getInstance().getPlaces(getApplication())
    }

    fun getPlaces() {
        placesEvent.value = Event.loading()
    }

}