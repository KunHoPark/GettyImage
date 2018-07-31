package com.leo.gettyimage.ui.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ccom.leo.gettyimage.adapter.GettyImageAdapter
import ccom.leo.gettyimage.ui.base.BaseFragment
import com.leo.gettyimage.application.Constants
import com.leo.gettyimage.callback.OnItemClickListener
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.databinding.MainFragmentBinding
import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.util.ActivityUtil
import com.leo.gettyimage.util.InfiniteScrollListener
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

// ViewFactory 타입의 의존성은 FragmentModule이 없으므로 부모인 ActivitySubComponent를 검색하여 MainModule에서 생성한다.
@ActivityScoped
class MainFragment @Inject constructor() : BaseFragment() {
    internal val tag = this.javaClass.simpleName

    private lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding: MainFragmentBinding
    @Inject lateinit var viewModelFactory: MainViewModelFactory

    private val gettyImageAdapter = GettyImageAdapter()
    private var currentPage = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = MainFragmentBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]

        viewDataBinding.run {
            this.mainViewModel = viewModel
            setLifecycleOwner(activity)

            recyclerView?.apply {
                val gridLayoutManager = GridLayoutManager(activity, 1)
                gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
                setHasFixedSize(true)
                adapter = gettyImageAdapter
                layoutManager = gridLayoutManager
                addOnScrollListener(InfiniteScrollListener({ loadData() }, gridLayoutManager))
            }
        }

        subscribeLiveData()
        loadData()
    }

    private fun loadData() {
        viewModel.loadCollections(Constants.LIMIT, currentPage * Constants.OFFSET, false)
        currentPage++
    }

    override fun onPause() {
        super.onPause()
        with(viewModel){
            disposeElements()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun initClickListener() {
        gettyImageAdapter?.let {
            it.setOnItemClickListener(object : OnItemClickListener {

                override fun onItemClick(item: Object, view: View, position: Int) {
                    item?.let {
                        it as GettyImageEntity
                        ActivityUtil.startPhotoActivityForResult(this@MainFragment, it.id, 100)
                    }
                }

            })
        }

    }

    private fun subscribeLiveData() {
        with(viewModel){
            isLoadingSuccess.observe(this@MainFragment, Observer<Boolean> {
                dataLoading.visibility = View.GONE
                if(it == false){
                    showToast("이미지 정보를 가져 오는데 실패 하였습니다.")
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Refresh list
        when (resultCode) {
            Activity.RESULT_OK -> {
                viewModel.loadCollections(viewModel.gettyImages.size, 0, true)
            }
        }
    }


}
