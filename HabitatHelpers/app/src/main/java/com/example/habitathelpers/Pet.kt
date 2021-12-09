package com.example.habitathelpers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pet (
    var name:String,
    var species:String,
    var gender:String,
    var age:Int
    //var temp:Int,
    //var humid:Int,
    //var space:Int

    // TODO: Finalize attributes of pet class

    ): Parcelable