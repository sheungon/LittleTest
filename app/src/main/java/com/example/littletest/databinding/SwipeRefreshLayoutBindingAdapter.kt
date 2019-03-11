package com.example.littletest.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * @author John Tsai
 */

@BindingMethods(
    BindingMethod(type = androidx.swiperefreshlayout.widget.SwipeRefreshLayout::class, attribute = "onRefresh", method = "setOnRefreshListener")
)
object SwipeRefreshLayoutBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["isRefreshing"])
    fun setRefreshing(
        view: androidx.swiperefreshlayout.widget.SwipeRefreshLayout,
        isRefreshing: Boolean
    ) {
        view.isRefreshing = isRefreshing
    }
}
