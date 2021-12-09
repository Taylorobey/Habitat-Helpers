package com.example.habitathelpers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hab (
    var name:String,
    var material:String,
    var substrate:String,
    var length:Int,
    var width:Int,
    var depth:Int,

    // TODO: finalize attributes for enclosure class
    ): Parcelable