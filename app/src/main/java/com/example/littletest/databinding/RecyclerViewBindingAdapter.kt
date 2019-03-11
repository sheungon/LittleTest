package com.example.littletest.databinding

import androidx.databinding.BindingAdapter
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author John Tsai
 */

object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["nestedScrollingEnabled"])
    fun setNestedScrollingEnabled(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
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
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        orientation: Int,
        reverseLayout: Boolean?,
        autoMeasureEnabled: Boolean?,
        nestedScrollingEnabled: Boolean?
    ) {
        val layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                recyclerView.context,
                orientation,
                reverseLayout == true
            )
        if (autoMeasureEnabled != null) {
            layoutManager.isAutoMeasureEnabled = autoMeasureEnabled
        }
        recyclerView.layoutManager = layoutManager
        if (nestedScrollingEnabled != null) {
            recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        }
    }
}