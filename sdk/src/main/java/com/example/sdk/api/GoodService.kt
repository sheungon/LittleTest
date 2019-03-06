package com.example.sdk.api

import com.example.sdk.model.Good
import com.example.sdk.utils.sortByPrice
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class GoodService {

    fun getGoodList(start: Int, end: Int): List<Good> {

        val list = ArrayList<Good>()

        for (i in start..end) {
            list.add(Good("Item $i", i.toFloat(), "$IMAGE_URL_BASE$i"))
        }

        list.sortByPrice()

        Thread.sleep(2000 + Random(Date().time).nextLong(1000))

        return list
    }

    companion object {
        const val IMAGE_URL_BASE = "https://picsum.photos/300/200?image="
    }
}