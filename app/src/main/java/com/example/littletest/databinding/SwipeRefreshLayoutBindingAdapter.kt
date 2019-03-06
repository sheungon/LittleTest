package com.example.littletest.databinding

import android.databinding.BindingAdapter
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.v4.widget.SwipeRefreshLayout

/**
 * @author John
 */

@BindingMethods(
    BindingMethod(type = SwipeRefreshLayout::class, attribute = "onRefresh", method = "setOnRefreshListener")
)
object SwipeRefreshLayoutBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["isRefreshing"])
    fun setRefreshing(
        view: SwipeRefreshLayout,
        isRefreshing: Boolean
    ) {
        view.isRefreshing = isRefreshing
    }
}
