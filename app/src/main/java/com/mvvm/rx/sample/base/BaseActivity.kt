package com.mvvm.rx.sample.base

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.mvvm.rx.sample.R
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

abstract class BaseActivity<VM: BaseViewModel,DB: ViewDataBinding> : AppCompatActivity(), IBaseView {

    protected lateinit var  viewModel: VM
    protected lateinit var dataBinding: DB

    abstract fun getLayoutId(): Int
    abstract fun getViewModelClass(): Class<VM>
    abstract fun getVariablesToBind(): Map<Int,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initViewModel()
        initView()
    }

    open fun initViewModel() {
        viewModel = obtainViewModel(getViewModelClass())
    }

    open fun initView() {
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        dataBinding.setLifecycleOwner(this)
        for ((variableId,value) in getVariablesToBind()) {
            dataBinding.setVariable(variableId,value)
        }
        dataBinding.executePendingBindings()
    }

    fun setToolbarTitle(textInt: Int) = toolbar?.setTitle(textInt)

    override fun isActive(): Boolean = !isFinishing

    override fun showAlert(textResource: Int) {
        if (isActive()) {
            alert(textResource) {
                yesButton { dialog -> dialog.dismiss() }
            }.show()
        }
    }

    override fun getViewContext(): Context = this


    fun replaceFragment(fragment: Fragment, @IdRes fragmentId: Int, tag: String) {
        try {
            if (isNewFragment(fragmentId, tag)) {
                supportFragmentManager.beginTransaction().replace(fragmentId, fragment, tag).commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isNewFragment(@IdRes fragmentId: Int, tag: String): Boolean = !getCurrentFragment(fragmentId)?.tag.equals(tag)

    private fun getCurrentFragment(@IdRes fragmentId: Int): Fragment? = supportFragmentManager.findFragmentById(fragmentId)

    override fun <T : BaseViewModel> obtainViewModel(clazz: Class<T>): T = ViewModelProviders.of(this).get(clazz)

    override fun onNetworkError() = showAlert(R.string.error_network)
}