package com.example.littletest.main

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableFloat
import com.example.littletest.STApplication
import com.example.sdk.api.GoodService
import com.example.sdk.model.Good
import com.sotwtm.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * View model for main page
 * @author John
 */

class MainViewModel
@Inject
constructor(
    application: STApplication,
    val goodService: GoodService
) {
    val isLoading = ObservableBoolean(false)
    val random = Random(Date().time)
    val goodList: ObservableArrayList<Good> = ObservableArrayList()
    val totalPrice = ObservableFloat(0f)
    val adapter = GoodItemAdapter(application, goodList, totalPrice)

    fun onCreate() {
        loadData()
    }

    fun onRefresh() {
        Log.d("onRefresh")
        goodList.clear()
        loadData()
    }

    @Synchronized
    fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                isLoading.set(true)
                val list = GlobalScope.async(Dispatchers.Default) {
                    goodService.getGoodList(0, 20)
                }.await()
                goodList.addAll(list)
            } catch (e: Exception) {
                Log.e("Error on load data", e)
            } finally {
                isLoading.set(false)
            }
        }
    }

    fun addGood() {
        val newGood = Good("A New ${random.nextInt(1000)}", random.nextInt(100) / 10f)
        goodList.add(0, newGood)
        Log.d("Added new good: ${newGood.name}")
    }
}
