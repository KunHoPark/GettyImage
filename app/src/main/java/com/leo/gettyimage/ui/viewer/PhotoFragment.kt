package com.leo.gettyimage.ui.viewer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ccom.leo.gettyimage.ui.base.BaseFragment
import com.leo.gettyimage.databinding.PhotoFragmentBinding
import com.leo.gettyimage.injection.scope.ActivityScoped
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.photo_fragment.*
import javax.inject.Inject

@ActivityScoped
class PhotoFragment @Inject constructor() : BaseFragment() {
    internal val tag = this.javaClass.simpleName

    private lateinit var viewModel: PhotoViewModel
    private lateinit var viewDataBinding: PhotoFragmentBinding
    @Inject lateinit var viewModelFactory: PhotoViewModelFactory

    private var currentPage = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = PhotoFragmentBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[PhotoViewModel::class.java]

        viewDataBinding.run {
            this.photoViewModel = viewModel
            setLifecycleOwner(activity)
        }

        loadData()
        subscribeLiveData()
    }

    private fun loadData() {
        viewModel.loadGettyImage()
    }

    override fun onDestroy() {
        super.onDestroy()
        with(viewModel){
            disposeElements()
        }
    }

    override fun initClickListener() {
        btnChagne.setOnClickListener {
        }
    }

    private fun subscribeLiveData() {
        with(viewModel){
            isLoadingSuccess.observe(this@PhotoFragment, Observer<Boolean> {
                dataLoading.visibility = View.GONE
                if(it == true){
//                    ActivityUtil.startBleScanActivity(activity!!)
                }else{
//                    ActivityUtil.startBleScanActivity(activity!!)
                    showToast("데이타 가져오기 실패!")
                }
            })

//            getGettyImage().observe(this@PhotoFragment, Observer<GettyImageEntity> {
//                it?.run {
//                    GlideImageUtils.loadImage(context!!, it.thumbnailUrl.toImageUrlForThumbnail(), imageView)
//                }
//            })
        }
    }



}
