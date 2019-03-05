package com.example.littletest.dto

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Keep

/**
 * @author John
 */
@Keep
class ColorJson(val colors: List<ColorData>) : Parcelable {

    constructor(source: Parcel) : this(
        source.createTypedArrayList(ColorData.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(colors)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ColorJson> = object : Parcelable.Creator<ColorJson> {
            override fun createFromParcel(source: Parcel): ColorJson = ColorJson(source)
            override fun newArray(size: Int): Array<ColorJson?> = arrayOfNulls(size)
        }
    }
}