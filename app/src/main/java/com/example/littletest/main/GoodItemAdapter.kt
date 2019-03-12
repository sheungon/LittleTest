package com.example.littletest.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_ID
import com.example.littletest.R
import com.example.littletest.databinding.ListItemAdsBinding
import com.example.littletest.databinding.ListItemGoodBinding
import com.example.sdk.model.Good
import com.sotwtm.util.Log
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference
import java.util.*


/**
 * @author John Tsai
 */
class GoodItemAdapter
constructor(
    context: Context,
    liveCycleOwner: LifecycleOwner,
    private val goodList: LiveData<MutableList<Good>>,
    private val totalPrice: MutableLiveData<Float>
) : RecyclerView.Adapter<GoodItemAdapter.ItemVH>() {

    private val picasso: Picasso = Picasso.Builder(context).build()

    init {
        // Do initialization here
        setHasStableIds(false)
        goodList.observe(liveCycleOwner, Observer<MutableList<Good>> {
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_ADS -> {
                val binding = DataBindingUtil.inflate<ListItemAdsBinding>(
                    inflater,
                    R.layout.list_item_ads,
                    parent,
                    false
                )
                AdsVH(this, binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ListItemGoodBinding>(
                    inflater,
                    R.layout.list_item_good,
                    parent,
                    false
                )
                GoodVH(this, binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {

        (holder as? GoodVH)?.let { goodVH ->
            val good = getItem(position)
            goodVH.dataBinding.good = good
            // TODO set item selected
            //goodVH.dataBinding.selected =
        } ?: (holder as? AdsVH)?.let { adsVH ->
            adsVH.dataBinding.url = getRandomAdsUrl()
        } ?: run {
            Log.wtf("Not possible! Check you codes!")
        }
    }

    override fun getItemViewType(position: Int): Int = TYPE_GOOD

    override fun getItemId(position: Int): Long = getItem(position)?.id ?: NO_ID

    override fun getItemCount(): Int = goodList.value?.size ?: 0

    fun getItem(position: Int): Good? =
        goodList.value?.getOrNull(position)

    fun onClickGood(good: Good, checked: Boolean) {
        Log.d("Checked [${good.name}] $checked")
        // TODO On Click good
    }

    fun updateTotalPrice() {
        // TODO updateTotalPrice
    }

    fun clearSelected() {
        Log.d("clearSelected")
        // TODO Clear all selected
    }


    ///////////////////// Class /////////////////////
    abstract class ItemVH(
        adpater: GoodItemAdapter,
        open val dataBinding: ViewDataBinding
    ) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(dataBinding.root) {
        protected val adapterRef = WeakReference(adpater)
    }

    class GoodVH(
        adapter: GoodItemAdapter,
        override val dataBinding: ListItemGoodBinding
    ) : ItemVH(adapter, dataBinding) {
        init {
            dataBinding.checkboxGood.setOnCheckedChangeListener() { _, checked ->
                adapterRef.get()?.let {
                    val good = it.getItem(adapterPosition) ?: return@setOnCheckedChangeListener
                    it.onClickGood(good, checked)
                }
            }
        }
    }

    class AdsVH(
        adapter: GoodItemAdapter,
        override val dataBinding: ListItemAdsBinding
    ) : ItemVH(adapter, dataBinding)


    companion object {

        val ADS_APPEAR_IN_EVERY = 10

        private val TYPE_GOOD = 0
        private val TYPE_ADS = 1

        private val RANDOM = Random(Date().time)

        private val ADS_NAMES =
            arrayOf("img_01.jpg", "img_02.jpg", "img_03.jpg", "img_04.jpg", "img_05.jpg")

        private fun String.toAndroidAssetPath(): String =
            "file:///android_asset/$this"

        private fun getRandomAdsUrl(): String =
            ADS_NAMES[RANDOM.nextInt(ADS_NAMES.size)].toAndroidAssetPath()
    }
}
