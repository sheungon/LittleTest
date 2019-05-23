package com.example.sdk.utils

import com.example.sdk.model.Good
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * Unit test for SortingUtils.kt
 * @author John Tsai
 * */
class SortingUtilsTest {

    val goodList = ArrayList<Good>()

    @Before
    fun prepare() {
        val random = Random(Date().time)
        for (i in 0 until 100) {
            val id = random.nextInt(1000)
            val price = random.nextInt(1000) / 10f
            goodList.add(Good("Item $id", price))
        }
    }

    @Test
    fun sortingTest() {

        goodList.sortByPrice(true)
        for (i in 1 until goodList.size) {
            Assert.assertTrue(
                "GoodList not sorted by price in acceding order",
                goodList[i - 1].price <= goodList[i].price
            )
        }

        goodList.sortByPrice(false)
        for (i in 1 until goodList.size) {
            Assert.assertTrue(
                "GoodList not sorted by price in descending order",
                goodList[i - 1].price >= goodList[i].price
            )
        }

        goodList.sortByName(true)
        for (i in 1 until goodList.size) {
            Assert.assertTrue(
                "GoodList not sorted by name in acceding order",
                goodList[i - 1].name <= goodList[i].name
            )
        }

        goodList.sortByName(false)
        for (i in 1 until goodList.size) {
            Assert.assertTrue(
                "GoodList not sorted by name in descending order",
                goodList[i - 1].name >= goodList[i].name
            )
        }
    }
}