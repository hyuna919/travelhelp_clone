package com.example.travel_help.DataClass

import android.os.Parcel
import android.os.Parcelable
import java.sql.Blob
import java.util.*

data class DataClassPost (val post_id:String?,
                          val type:String?,
                          val title:String?,
                          val content: String?,
                          val writer_id: String?,
                          val createdAt: String?,
                          val country: String?,
                          val latitude: Int?,
                          val longitude: Int?,
                          val image: Blob?,
                          val recommended: Int?)