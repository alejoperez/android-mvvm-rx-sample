package com.mvvm.rx.sample.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * from user LIMIT 1")
    fun getUser(): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

}

@Dao
interface PlaceDao {

    @Query("SELECT * from place")
    fun getPlaces(): Single<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlaces(places: List<Place>)

}

@Dao
interface PhotoDao {

    @Query("SELECT * from photo")
    fun getPhotos(): Single<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePhotos(photos: List<Photo>)
}