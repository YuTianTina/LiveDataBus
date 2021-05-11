package com.yutiantina.livedatabus

import android.os.Parcel
import android.os.Parcelable

data class IPCEntity(
    var name: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<IPCEntity> = object : Parcelable.Creator<IPCEntity> {
            override fun createFromParcel(source: Parcel): IPCEntity = IPCEntity(source)
            override fun newArray(size: Int): Array<IPCEntity?> = arrayOfNulls(size)
        }
    }
}