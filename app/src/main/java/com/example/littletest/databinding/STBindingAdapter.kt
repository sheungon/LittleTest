package com.example.littletest.databinding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView

import com.example.littletest.dto.ColorJson
import com.example.littletest.main.ColorDataItemAdapter

/**
 * @author John
 */

object STBindingAdapter {

    @BindingAdapter(value = ["setColorJson"])
    @JvmStatic
    fun setData(
        recyclerView: RecyclerView,
        json: ColorJson?
    ) {

        if (json != null) {
            val adapter = recyclerView.getAdapter()
            if (adapter is ColorDataItemAdapter) {
                (adapter as ColorDataItemAdapter).colorJson = json
            } else {
                recyclerView.setAdapter(ColorDataItemAdapter(json))
            }
        } else {
            recyclerView.setAdapter(null)
        }
    }
}
