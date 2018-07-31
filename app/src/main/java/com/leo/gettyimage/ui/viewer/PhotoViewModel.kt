package com.leo.gettyimage.ui.viewer


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.leo.gettyimage.callback.OnLoadListener
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.data.repository.ImageDetailRepository
import com.leo.gettyimage.util.LeoLog
import javax.inject.Inject

class PhotoViewModel
@Inject constructor(private val imageDetailRepository: ImageDetailRepository, private val imageId: String) : ViewModel() {
    internal val tag = this.javaClass.simpleName

    var isLoadingSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var isShowBottomLayout: ObservableInt = ObservableInt(View.VISIBLE)
    var gettyImage= ObservableField<GettyImageEntity>()

    fun loadGettyImage() {
        //Getty site로 부터 파서 후 이미지의 상세 정보 가져 오기. 가져온 이미지 정보를 DB 저장 후 결과.
        gettyImage.set(imageDetailRepository.getGettyImage(imageId, object : OnLoadListener{
            override fun onSuccess(gettyImageEntity: List<GettyImageEntity>?) {
                gettyImageEntity?.let {
                    gettyImage.set(it[0])
                    isLoadingSuccess.postValue(true)
                }
            }

            override fun onFail(error: String?) {
                LeoLog.e(tag, error!!)
                isLoadingSuccess.postValue(false)
            }
        }))
    }

    fun isShowBottomLayout(value: Int){
        when(value){
            View.VISIBLE -> isShowBottomLayout.set(View.GONE)
            View.GONE -> isShowBottomLayout.set(View.VISIBLE)
        }
    }

    fun disposeElements() {
        imageDetailRepository.compositeDisposable.clear()
    }
}
