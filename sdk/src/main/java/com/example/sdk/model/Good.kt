package com.example.sdk.model

import android.os.Parcel
import android.os.Parcelable

data class Good(
    val name: String = "",
    val price: Float = 0f,
    val iconUrl: String? = null
) : Parcelable {

    val id: Long by lazy { idCounter++ }

    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readFloat(),
        source.readString() ?: ""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeFloat(price)
        writeString(iconUrl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Good> = object : Parcelable.Creator<Good> {
            override fun createFromParcel(source: Parcel): Good = Good(source)
            override fun newArray(size: Int): Array<Good?> = arrayOfNulls(size)
        }

        @Volatile
        var idCounter: Long = 0
    }
}