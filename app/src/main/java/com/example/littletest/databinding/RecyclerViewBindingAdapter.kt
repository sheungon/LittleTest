package com.example.littletest.databinding

import android.databinding.BindingAdapter
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * @author John
 */

object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["nestedScrollingEnabled"])
    fun setNestedScrollingEnabled(
        recyclerView: RecyclerView,
        enabled: Boolean
    ) {
        ViewCompat.setNestedScrollingEnabled(recyclerView, enabled)
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "setLayoutManagerOrientation",
            "setLayoutManagerReverseLayout",
            "setAutoMeasureEnabled",
            "setNestedScrollingEnabled"],
        requireAll = false
    )
    fun setLayoutManager(
        recyclerView: RecyclerView,
        orientation: Int,
        reverseLayout: Boolean?,
        autoMeasureEnabled: Boolean?,
        nestedScrollingEnabled: Boolean?
    ) {
        val layoutManager =
            LinearLayoutManager(recyclerView.context, orientation, reverseLayout == true)
        if (autoMeasureEnabled != null) {
            layoutManager.isAutoMeasureEnabled = autoMeasureEnabled
        }
        recyclerView.layoutManager = layoutManager
        if (nestedScrollingEnabled != null) {
            recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        }
    }
}