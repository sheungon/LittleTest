package com.example.littletest.main

import androidx.lifecycle.MutableLiveData
import com.example.littletest.STApplication
import com.example.sdk.api.GoodService
import com.example.sdk.model.Good
import com.sotwtm.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * View model for main page
 * @author John Tsai
 */

class MainViewModel
@Inject
constructor(
    application: STApplication,
    activity: MainActivity,
    val goodService: GoodService
) {
    val isLoading = MutableLiveData<Boolean>(false)
    val random = Random(Date().time)
    val goodList = MutableLiveData<MutableList<Good>>(ArrayList())
    val totalPrice = MutableLiveData<Float>(0f)
    val adapter = GoodItemAdapter(application, activity, goodList, totalPrice)

    fun onCreate() {
        loadData()
    }

    fun onRefresh() {
        Log.d("onRefresh")
        goodList.value?.clear()
        goodList.value = goodList.value
        loadData()
    }

    @Synchronized
    fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                isLoading.postValue(true)
                val list = withContext(Dispatchers.Default) {
                    goodService.getGoodList(0, 20)
                }
                goodList.value?.addAll(list)
                goodList.value = goodList.value
            } catch (e: Exception) {
                Log.e("Error on load data", e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun addGood() {
        val newGood = Good("A New ${random.nextInt(1000)}", random.nextInt(100) / 10f)
        goodList.value?.add(0, newGood)
        goodList.value = goodList.value
        Log.d("Added new good: ${newGood.name} $${newGood.price}")
    }
}
