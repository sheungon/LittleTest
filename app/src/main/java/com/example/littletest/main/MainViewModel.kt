package com.example.littletest.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littletest.STApplication
import com.example.sdk.api.GoodService
import com.example.sdk.model.Good
import com.sotwtm.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val goodService: GoodService
) : ViewModel() {
    private val random = Random(Date().time)
    private val loadingTask = MutableLiveData<Job?>()
    private val goodList = MutableLiveData<MutableList<Good>>(ArrayList())

    val isLoading = Transformations.map(loadingTask) {
        it != null && !it.isCompleted
    }
    val totalPrice = MutableLiveData(0f)
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

    private fun loadData() {
        if (isLoading.value == true) return
        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    goodService.getGoodList(0, 20)
                }
                goodList.value?.addAll(list)
                goodList.value = goodList.value
            } catch (e: Exception) {
                Log.e("Error on load data", e)
            } finally {
                loadingTask.value = null
            }
        }.also {
            loadingTask.value = it
        }
    }

    fun addGood() {
        val newGood = Good("A New ${random.nextInt(1000)}", random.nextInt(100) / 10f)
        goodList.value?.add(0, newGood)
        goodList.value = goodList.value
        Log.d("Added new good: ${newGood.name} $${newGood.price}")
    }
}
