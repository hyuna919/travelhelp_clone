package com.example.travel_help.DataClass

import android.os.Parcel
import android.os.Parcelable

data class DataClassChatting(val sender:String, val receiver: String, val date: String, val time: String, val message: String)