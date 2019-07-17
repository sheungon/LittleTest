package com.example.littletest.databinding

import androidx.databinding.BindingConversion
import com.example.sdk.model.Good

/**
 * Binding function for [Good]
 * @author John Tsai
 * */
object GoodBindingAdapter {

    @JvmStatic
    @BindingConversion
    fun toString(good: Good?) = good?.name
}