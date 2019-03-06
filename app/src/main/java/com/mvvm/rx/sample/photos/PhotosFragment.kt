package com.mvvm.rx.sample.photos

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mvvm.rx.sample.BR
import com.mvvm.rx.sample.R
import com.mvvm.rx.sample.base.BaseFragment
import com.mvvm.rx.sample.base.BaseRecyclerViewAdapter
import com.mvvm.rx.sample.data.room.Photo
import com.mvvm.rx.sample.databinding.FragmentPhotosBinding
import com.mvvm.rx.sample.livedata.Event
import com.mvvm.rx.sample.livedata.Status
import com.mvvm.rx.sample.view.SimpleDividerItemDecorator

class PhotosFragment : BaseFragment<PhotosViewModel,FragmentPhotosBinding>(), BaseRecyclerViewAdapter.OnItemClickListener {

    companion object {
        const val TAG = "PhotosFragment"
        fun newInstance() = PhotosFragment()
    }


    override fun getLayoutId(): Int = R.layout.fragment_photos
    override fun getViewModelClass(): Class<PhotosViewModel> = PhotosViewModel::class.java
    override fun getVariablesToBind(): Map<Int, Any> = mapOf(
            BR.viewModel to viewModel
    )


    override fun initViewModel() {
        super.initViewModel()
        viewModel.photos.observe(this, onPhotosResponseObserver)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)
        viewModel.getPhotos()
    }

    private val onPhotosResponseObserver = Observer<Event<List<Photo>>> {
        if (it != null) {
            onPhotosResponse(it)
        } else {
            onPhotosFailure()
        }
    }

    private fun onPhotosResponse(response: Event<List<Photo>>) {
        when(response.status) {
            Status.SUCCESS -> onPhotosSuccess(response.peekData())
            Status.FAILURE -> onPhotosFailure()
            Status.NETWORK_ERROR -> onNetworkError()
            else -> Unit
        }
    }

    private fun onPhotosSuccess(photos: List<Photo>?) {
        dataBinding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(getViewContext())
            setHasFixedSize(true)
            addItemDecoration(SimpleDividerItemDecorator(getViewContext()))
            adapter = PhotosAdapter(photos, this@PhotosFragment)
        }
    }

    private fun onPhotosFailure() {
        showAlert(R.string.error_loading_photos)
    }

    override fun onItemClicked(item: Any?) {
        PhotoDetailDialogFragment.newInstance(item as Photo).show(fragmentManager, PhotoDetailDialogFragment.TAG)
    }

}