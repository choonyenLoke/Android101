package com.example.task1

import android.os.Parcel
import android.os.Parcelable

data class Item (val mImageRes: Int, val mTitle: String?, val mSub: String?, val mDesc: String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mImageRes)
        parcel.writeString(mTitle)
        parcel.writeString(mSub)
        parcel.writeString(mDesc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}