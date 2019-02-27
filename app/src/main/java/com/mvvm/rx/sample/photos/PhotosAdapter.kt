package com.mvvm.rx.sample.photos

import com.mvvm.rx.sample.R
import com.mvvm.rx.sample.base.BaseRecyclerViewAdapter
import com.mvvm.rx.sample.data.room.Photo

class PhotosAdapter(photoList: List<Photo>?,listener: BaseRecyclerViewAdapter.OnItemClickListener) : BaseRecyclerViewAdapter(photoList,listener) {

    override fun getItemLayoutId(): Int = R.layout.item_photo

}