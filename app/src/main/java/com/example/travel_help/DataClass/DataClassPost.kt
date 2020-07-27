package com.example.travel_help.DataClass

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class DataClassPost (val title:String?, val date: Int, val airport: String?, val content: String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(date)
        parcel.writeString(airport)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataClassPost> {
        override fun createFromParcel(parcel: Parcel): DataClassPost {
            return DataClassPost(parcel)
        }

        override fun newArray(size: Int): Array<DataClassPost?> {
            return arrayOfNulls(size)
        }
    }
}