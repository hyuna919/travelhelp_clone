package com.example.travel_help.DataClass

import android.os.Parcel
import android.os.Parcelable

class DataClassAccount(id:String, pw:String):Parcelable{
    constructor(parcel: Parcel) : this(
        TODO("id"),
        TODO("pw")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataClassAccount> {
        override fun createFromParcel(parcel: Parcel): DataClassAccount {
            return DataClassAccount(parcel)
        }

        override fun newArray(size: Int): Array<DataClassAccount?> {
            return arrayOfNulls(size)
        }
    }
}