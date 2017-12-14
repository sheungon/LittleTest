package com.example.littletest.dto

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import com.sotwtm.util.Log
import java.util.*

/**
 * An object to store color codes
 * @author John
 */
class ColorCode(val rgba: IntArray,
                val hex: String) : Parcelable {

    fun rgbaToColor(): Int = try {
        Color.argb(rgba[3], rgba[0], rgba[1], rgba[2])
    } catch (e: Exception) {
        Log.e("Error when read color : " + Arrays.toString(rgba), e)
        Color.BLACK
    }

    fun hexToColor(): Int = try {
        Color.parseColor(hex)
    } catch (e: Exception) {
        Log.e("Error when parse : $hex", e)
        Color.BLACK
    }

    constructor(source: Parcel) : this(
            source.createIntArray(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeIntArray(rgba)
        writeString(hex)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ColorCode> = object : Parcelable.Creator<ColorCode> {
            override fun createFromParcel(source: Parcel): ColorCode = ColorCode(source)
            override fun newArray(size: Int): Array<ColorCode?> = arrayOfNulls(size)
        }
    }
}