package com.leo.gettyimage.ui.ble

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ccom.leo.gettyimage.adapter.BleScanAdapter
import ccom.leo.gettyimage.ui.base.BaseFragment
import com.leo.gettyimage.R
import com.leo.gettyimage.application.Constants
import com.leo.gettyimage.databinding.BleScanFragmentBinding
import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.util.InfiniteScrollListener
import kotlinx.android.synthetic.main.ble_scan_fragment.*
import javax.inject.Inject

/**
 * BleScanFragment
 * @author KunHoPark
 * @since 2018. 7. 17. AM 10:43
 **/
@ActivityScoped
class BleScanFragment @Inject constructor() : BaseFragment() {
    internal val tag = this.javaClass.simpleName

    private lateinit var viewModel: BleScanViewModel
    private lateinit var viewDataBinding: BleScanFragmentBinding
    @Inject lateinit var viewModelFactory: BleScanViewModelFactory

    private val bleScanAdapter = BleScanAdapter()
    var currentPage = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = BleScanFragmentBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[BleScanViewModel::class.java]

        viewDataBinding.run {
            this.bleScanViewModel = viewModel

            rvBle?.apply {
                val gridLayoutManager = GridLayoutManager(activity, 1)
                gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
                setHasFixedSize(true)
                adapter = bleScanAdapter
                layoutManager = gridLayoutManager
                addOnScrollListener(InfiniteScrollListener({ loadData() }, gridLayoutManager))
            }
        }

        subscribeLiveData()
        loadData()

    }

    fun loadData() {
        viewModel.loadCryptocurrencies(Constants.LIMIT, currentPage * Constants.OFFSET)
        currentPage++
    }

    override fun onDestroy() {
        viewModel.disposeElements()
        super.onDestroy()
    }



    override fun initClickListener() {

//        bleScanAdapter?.run {
//            this.setOnItemClickListener(object : OnItemClickListener {
//                override fun onItemClick(item: Object, view: View, position: Int) {
//                    val bleData = item as BleData
//                    KonaLog.i(tag, "setOnItemClickListener item=${bleData.bleDevice!!.name}, view=$view, position=$position")
//                    viewModel.selectedBleDevice(position)
//                }
//
//            })
//        }

    }

    private fun subscribeLiveData() {
        with(viewModel){

            //Update to the BleScanAdapter list date
//            bleListUpdateLiveEvent.observe(activity!!, Observer { bleScanAdapter.notifyDataSetChanged() })

//            viewDisposables += this.isLoading
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe { isLoading ->
//                        if (isLoading) dataLoading.toVisibile()
//                        else dataLoading.toGone()
//                    }


            cryptocurrenciesError().observe(this@BleScanFragment, Observer<String> {
                if (it != null) {
                    android.widget.Toast.makeText(activity, resources.getString(R.string.cryptocurrency_error_message) + it,
                            android.widget.Toast.LENGTH_SHORT).show()
                }
            })

            cryptocurrenciesLoader().observe(this@BleScanFragment, Observer<Boolean> {
                if (it == false) dataLoading.visibility = android.view.View.GONE
            })
        }

    }


}
