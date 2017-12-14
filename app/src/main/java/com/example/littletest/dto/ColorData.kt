package com.example.littletest.dto

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Keep

/**
 * A color data
 * @author John
 */
@Keep
class ColorData(val color: String,
                val code: ColorCode) : Parcelable {

    fun name() = color

    constructor(source: Parcel) : this(
            source.readString(),
            source.readParcelable<ColorCode>(ColorCode::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(color)
        writeParcelable(code, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ColorData> = object : Parcelable.Creator<ColorData> {
            override fun createFromParcel(source: Parcel): ColorData = ColorData(source)
            override fun newArray(size: Int): Array<ColorData?> = arrayOfNulls(size)
        }
    }
}
