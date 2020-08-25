package com.example.travel_help.DataClass

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class DataClassPost (val title:String?, val date: String?, val airport: String?, val content: String?, val writer_id: String?, val createdAt: String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(airport)
        parcel.writeString(content)
        parcel.writeString(writer_id)
        parcel.writeString(createdAt)
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