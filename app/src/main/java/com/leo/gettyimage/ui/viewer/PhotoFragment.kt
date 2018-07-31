package com.leo.gettyimage.ui.viewer

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ccom.leo.gettyimage.ui.base.BaseFragment
import com.leo.gettyimage.databinding.PhotoFragmentBinding
import com.leo.gettyimage.injection.scope.ActivityScoped
import kotlinx.android.synthetic.main.photo_fragment.*
import javax.inject.Inject

@ActivityScoped
class PhotoFragment @Inject constructor() : BaseFragment() {
    internal val tag = this.javaClass.simpleName

    private lateinit var viewModel: PhotoViewModel
    private lateinit var viewDataBinding: PhotoFragmentBinding
    @Inject lateinit var viewModelFactory: PhotoViewModelFactory


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

    override fun onPause() {
        super.onPause()
        with(viewModel){
            disposeElements()
        }
    }

    private fun loadData() {
        viewModel.loadGettyImage()
    }

    override fun initClickListener() {
        with(viewModel){
            imageView.setOnOutsidePhotoTapListener {
                isShowBottomLayout(isShowBottomLayout.get())
            }
            imageView.setOnPhotoTapListener { _, _, _ ->
                isShowBottomLayout(isShowBottomLayout.get())
            }

            btnImageClosed.setOnClickListener {
                activity!!.setResult(Activity.RESULT_OK)
                activity!!.finish()
            }
        }
    }

    private fun subscribeLiveData() {
        with(viewModel){
            isLoadingSuccess.observe(this@PhotoFragment, Observer<Boolean> {

                if(it == false){
                    showToast("이미지의 상세 정보를 가져 오는데 실패 하였습니다.")
                }
            })
        }
    }



}
