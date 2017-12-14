package com.example.littletest.main

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_ID
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.littletest.R
import com.example.littletest.STApplication
import com.example.littletest.databinding.ListItemAdsBinding
import com.example.littletest.databinding.ListItemContentBinding
import com.example.littletest.dto.ColorData
import com.example.littletest.dto.ColorJson
import com.sotwtm.util.Log
import com.squareup.picasso.Picasso
import java.io.IOException
import java.io.InputStream
import java.util.*


/**
 */

class ColorDataItemAdapter(_colorJson: ColorJson) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val adsNames = SparseArray<String>()
    val random = Random()
    val picasso: Picasso

    init {
        setHasStableIds(true)

        picasso = Picasso.Builder(STApplication.instance).build()
    }

    var colorJson: ColorJson = _colorJson
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_ADS -> {
                val binding = DataBindingUtil.inflate<ListItemAdsBinding>(inflater, R.layout.list_item_ads, parent, false)
                AdsVH(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ListItemContentBinding>(inflater, R.layout.list_item_content, parent, false)
                ContentVH(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as? ContentVH)?.let { contentVH ->
            contentVH.binding.item = getItem(position)
        } ?: (holder as? AdsVH)?.let { adsVH ->
            adsVH.binding.imageView.setImageBitmap(getRandomAds())
        } ?: {
            Log.wtf("Not possible! Check you codes!")
        }.invoke()
    }

    override fun getItemId(position: Int): Long = NO_ID

    override fun getItemViewType(position: Int): Int =
            if (ADS_APPEAR_IN_ROW != 0 && position % (ADS_APPEAR_IN_ROW + 1) == ADS_APPEAR_IN_ROW) TYPE_ADS
            else TYPE_CONTENT

    override fun getItemCount(): Int =
            colorJson.colors.size +
                    if (ADS_APPEAR_IN_ROW != 0) colorJson.colors.size / ADS_APPEAR_IN_ROW
                    else 0

    fun getItem(position: Int): ColorData? =
            colorJson.colors.getOrNull(position -
                    if (ADS_APPEAR_IN_ROW != 0) position / ADS_APPEAR_IN_ROW
                    else 0)

    fun getRandomAds(): Bitmap? =
            loadAds(STApplication.instance, getRandomAdsName())

    fun getRandomAdsName(): String =
            ADS_NAMES[random.nextInt(ADS_NAMES.size)]


    class ContentVH(val binding: ListItemContentBinding) : RecyclerView.ViewHolder(binding.root)

    class AdsVH(val binding: ListItemAdsBinding) : RecyclerView.ViewHolder(binding.root)


    companion object {

        val ADS_APPEAR_IN_ROW = 0

        val TYPE_CONTENT = 0
        val TYPE_ADS = 1

        val ADS_NAMES = arrayOf("img_01.jpg", "img_02.jpg", "img_03.jpg", "img_04.jpg", "img_05.jpg")

        fun loadAds(context: Context, filePath: String): Bitmap? {
            val assetManager = context.getAssets()

            val istr: InputStream
            var bitmap: Bitmap? = null
            try {
                istr = assetManager.open(filePath)
                bitmap = BitmapFactory.decodeStream(istr)
            } catch (e: IOException) {
                Log.e("Error on load assets : $filePath", e)
            }

            return bitmap
        }

        fun toAndroidFilePath(assetSubPath: String): String =
                "file:///android_asset/$assetSubPath"
    }
}
