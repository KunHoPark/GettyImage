package com.leo.gettyimage.callback

import com.leo.gettyimage.data.local.GettyImageEntity

interface OnLoadListener {
    fun onSuccess(gettyImageEntity: List<GettyImageEntity>?)
    fun onFail(error: String?)
}