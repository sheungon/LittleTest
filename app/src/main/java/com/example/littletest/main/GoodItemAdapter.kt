package com.example.littletest.main

import android.content.Context
import android.databinding.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_ID
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.littletest.R
import com.example.littletest.databinding.ListItemAdsBinding
import com.example.littletest.databinding.ListItemGoodBinding
import com.example.sdk.model.Good
import com.sotwtm.util.Log
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference
import java.util.*


/**
 * @author sheungon
 */
class GoodItemAdapter
constructor(
    context: Context,
    val goodList: ObservableArrayList<Good>,
    val totalPrice: ObservableFloat
) : RecyclerView.Adapter<GoodItemAdapter.ItemVH>() {

    private val picasso: Picasso = Picasso.Builder(context).build()

    init {
        setHasStableIds(false)
        // Do initialization here
        goodList.addOnListChangedCallback(GoodListChangedListener(this))
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
            picasso.load(getRandomAdsUrl())
                .fit()
                .into(adsVH.dataBinding.imageView)
        } ?: run {
            Log.wtf("Not possible! Check you codes!")
        }
    }

    override fun getItemViewType(position: Int): Int = TYPE_GOOD

    override fun getItemId(position: Int): Long = getItem(position)?.id ?: NO_ID

    override fun getItemCount(): Int = goodList.size

    fun getItem(position: Int): Good? =
        goodList.getOrNull(position)

    fun onClickGood(good: Good, checked: Boolean) {
        // TODO On Click good
    }

    fun updateTotalPrice() {
        // TODO updateTotalPrice
    }

    fun clearSelected() {
        // TODO Clear all selected
    }


    ///////////////////// Class /////////////////////
    abstract class ItemVH(
        adpater: GoodItemAdapter,
        open val dataBinding: ViewDataBinding
    ) :
        RecyclerView.ViewHolder(dataBinding.root) {
        protected val adapterRef = WeakReference(adpater)
    }

    class GoodVH(
        adpater: GoodItemAdapter,
        override val dataBinding: ListItemGoodBinding
    ) : ItemVH(adpater, dataBinding) {
        init {
            dataBinding.checkboxGood.setOnCheckedChangeListener() { view, checked ->
                adapterRef.get()?.let {
                    val good = it.getItem(adapterPosition) ?: return@setOnCheckedChangeListener
                    it.onClickGood(good, checked)
                }
            }
        }
    }

    class AdsVH(
        adpater: GoodItemAdapter,
        override val dataBinding: ListItemAdsBinding
    ) : ItemVH(adpater, dataBinding)

    class GoodListChangedListener(adapter: GoodItemAdapter) :
        ObservableList.OnListChangedCallback<ObservableArrayList<Good>>() {

        private val adapterRef = WeakReference(adapter)

        override fun onChanged(sender: ObservableArrayList<Good>?) {
            adapterRef.get()?.apply {
                notifyDataSetChanged()
                clearSelected()
            }
        }

        override fun onItemRangeRemoved(
            sender: ObservableArrayList<Good>?,
            positionStart: Int,
            itemCount: Int
        ) {
            adapterRef.get()?.apply {
                notifyDataSetChanged()
                clearSelected()
            }
        }

        override fun onItemRangeMoved(
            sender: ObservableArrayList<Good>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            adapterRef.get()?.notifyDataSetChanged()
        }

        override fun onItemRangeInserted(
            sender: ObservableArrayList<Good>?,
            positionStart: Int,
            itemCount: Int
        ) {
            adapterRef.get()?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(
            sender: ObservableArrayList<Good>?,
            positionStart: Int,
            itemCount: Int
        ) {
            adapterRef.get()?.apply {
                notifyDataSetChanged()
                clearSelected()
            }
        }
    }


    companion object {

        private val ADS_APPEAR_IN_EVERY = 10

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
