package com.leo.gettyimage.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ccom.leo.gettyimage.ui.base.BaseFragment
import com.leo.gettyimage.databinding.SplashFragmentBinding
import com.leo.gettyimage.injection.scope.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SplashFragment @Inject constructor() : BaseFragment() {
    internal val tag = this.javaClass.simpleName

    private lateinit var viewModel: SplashViewModel
    private lateinit var viewDataBinding: SplashFragmentBinding
    @Inject lateinit var viewModelFactory: SplashViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = SplashFragmentBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]

        viewDataBinding.run {
            this.viewModel = viewModel

        }

        subscribeLiveData()
        loadData()

    }

    fun loadData() {
    }

    override fun onDestroy() {
        super.onDestroy()
    }



    override fun initClickListener() {

    }

    private fun subscribeLiveData() {
        with(viewModel){

        }
    }


}
