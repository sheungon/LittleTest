package com.example.littletest.databinding;

import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.littletest.dto.ColorJson;
import com.example.littletest.main.ColorDataItemAdapter;

/**
 * @author John
 */

public class STBindingAdapter {

    @BindingAdapter(value = "bind:setColorJson")
    public static void setData(RecyclerView recyclerView,
                               @Nullable ColorJson json) {

        if (json != null) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter instanceof ColorDataItemAdapter) {
                ((ColorDataItemAdapter) adapter).setColorJson(json);
            } else {
                recyclerView.setAdapter(new ColorDataItemAdapter(json));
            }
        } else {
            recyclerView.setAdapter(null);
        }
    }
}
